package com.yidian.wemedia;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yidian.data.etl.ColumnInfo;
import yidian.data.etl.EncodeUtils;
import yidian.data.etl.olap.AggOperator;
import yidian.data.meta.types.StatProtos.StatValue;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 数据按照小时导入到cf=f中,每天会合并小时的数据为天级别，存放在cf=c中(暂只对source表如此操作)
 */
public abstract class StatService {
    public static final String HBASE_CLIENT_POOL_SIZE = "hbase.connection.pool.size";
    public static final String DOC_STAT_TABLE_NAME = "stat.table.doc";
    public static final String SOURCE_STAT_TABLE_NAME = "stat.table.source";

    private static final Logger logger = LoggerFactory.getLogger(StatService.class);
    private static final int DEFAULT_POOL_SIZE = 32;
    public static final byte[] FAMILY = "f".getBytes();  // 小时更新的column family
    public static final byte[] DAILY_FAMILY =  "c".getBytes(); // 按照天聚集的 column family

    protected final HBaseClient hBaseClient;
    private final TableName docByDay;
    private final TableName sourceByDay;
    private final Descriptors.Descriptor descriptor = StatValue.getDescriptor();

    protected final AggOperator aggOperator;


    public static class HBaseClient {
        private static final Logger logger = LoggerFactory.getLogger(HBaseClient.class);
        public static final String HBASE_ZOOKEEPER_QUORUM = HConstants.ZOOKEEPER_QUORUM;
        public static final String HBASE_ZOOKEEPER_CLIENT_PORT = HConstants.ZOOKEEPER_CLIENT_PORT;
        private final ExecutorService threadPool;
        private AtomicBoolean isOpen = new AtomicBoolean(false);
        private final Configuration conf;
        private HConnection hConnection;

        public HBaseClient(Configuration conf, int poolSize) throws IOException {
            this.conf = conf;
            threadPool = Executors.newFixedThreadPool(poolSize);
//            HConstants.CLIENT_C
            open();
        }

        public Configuration getConf() {
            return conf;
        }

        private void open() throws IOException {
            if (isOpen.compareAndSet(false, true)) {
                hConnection = HConnectionManager.createConnection(conf, threadPool);
                logger.info("open hbase client");
            }
        }

        public void close() {
            if (isOpen.compareAndSet(true, false)) {
                try {
                    logger.info("close hbase connection");
                    hConnection.close();
                    hConnection = null;
                } catch (IOException ie) {
                    logger.info("close connection com.yidian.exception", ie);
                }
                try {
                    threadPool.shutdown();
                    threadPool.awaitTermination(30, TimeUnit.SECONDS);
                    threadPool.shutdownNow();
                } catch (Exception e) {
                    logger.error("failed to close ", e);
                }
            }
        }

        public HTableInterface getTable(TableName tableName) throws IOException {
            HTableInterface hTableInterface = hConnection.getTable(tableName);
            return hTableInterface;
        }
    }

    public StatService(Configuration conf) {
        docByDay = TableName.valueOf(DOC_STAT_TABLE_NAME);
        sourceByDay = TableName.valueOf(SOURCE_STAT_TABLE_NAME);

        try {
            int poolSize = conf.getInt(HBASE_CLIENT_POOL_SIZE, DEFAULT_POOL_SIZE);
            hBaseClient = new HBaseClient(conf, poolSize);
            hBaseClient.open();
            aggOperator = new AggOperator(StatValue.getDescriptor());
        } catch (IOException ie) {
            throw new IllegalArgumentException(ie);
        }
    }

    // Todo: check the difference between 0 and 1 of ColumnInfo
    // @Notice 要求timestamp的精度到小时级,会忽略分钟和秒
    public static Filter getTimeRange(long startTimestamp, long endTimestamp) {
        ColumnInfo startCol = new ColumnInfo(0, startTimestamp);
        ColumnInfo endCol = new ColumnInfo(0, endTimestamp);
        QualifierFilter filter1 = new QualifierFilter(CompareOp.GREATER_OR_EQUAL,
                new BinaryComparator(startCol.getQualifier(TimeUnit.HOURS)));
        QualifierFilter filter2 = new QualifierFilter(CompareOp.LESS,
                new BinaryComparator(endCol.getQualifier(TimeUnit.HOURS)));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(filter1);
        filterList.addFilter(filter2);
        return filterList;
    }

    public StatValue toStatValue(List<Object> values) {
        StatValue.Builder builder = StatValue.newBuilder();
        for (int index = 0; index < values.size(); ++index) {
            Descriptors.FieldDescriptor fieldDescriptor = descriptor.findFieldByNumber(index + 1);
            builder.setField(fieldDescriptor, values.get(index));
        }
        return builder.build();
    }

    public Map<String, StatValue> getStatByDay(TableName tableName, String row,
                                                          long beginTimeInSec, long endTimeInSec) throws Exception {
        return new HashMap<>(1);
    }

    public Map<String, StatValue> getStat(TableName tableName, Collection<String> rows) throws IOException {
        return new HashMap<>(1);
    }

    public Map<String, StatValue> getStat(TableName tableName, Collection<String> rows,
                                                     long beginTimeInSec, long endTimeInSec) throws IOException {
        return new HashMap<>(1);
    }


    /**
     * 取最新时间点的真实数据和优化数据
     * @param tableName
     * @param gets
     * @return
     * @throws Exception
     */
    public Map<String, StatValue> getStatByMax(TableName tableName, List<Get> gets) throws Exception {

        HTableInterface hTable = hBaseClient.getTable(tableName);

        Result[] results = hTable.get(gets);

        Map<String, StatValue> output = new HashMap<>();

        for (Result result : results) {
            if (result.isEmpty()) {
                continue;
            }

            long tmpTime = 0;
            long maxTime = 0;
            List<ByteString> maxBsList = new ArrayList<>(2);

            for (Cell cell : result.rawCells()) {
                ColumnInfo columnInfo = ColumnInfo.parseFrom(cell.getQualifierArray(), cell.getQualifierOffset(),
                        cell.getQualifierLength());

                tmpTime = columnInfo.getTimestampInSec();
                if(tmpTime == maxTime){
                    maxBsList.add(ByteString.copyFrom(
                            cell.getValueArray(),
                            cell.getValueOffset(),
                            cell.getValueLength()));
                }else if(tmpTime > maxTime){
                    maxBsList.clear();
                    maxTime = tmpTime;
                    maxBsList.add(ByteString.copyFrom(
                            cell.getValueArray(),
                            cell.getValueOffset(),
                            cell.getValueLength()));
                }
            }

            AggOperator tmpAggOperator = getLocalAggOperator(aggOperator);

            tmpAggOperator.startNewAgg();

            for(ByteString maxBs: maxBsList) {
                tmpAggOperator.putTuple(maxBs);
            }

            List<Object> aggValues = tmpAggOperator.endAgg();

            String key = EncodeUtils.decodeRow(result.getRow(), 0, result.getRow().length);
            output.put(key, toStatValue(aggValues));
        }

        return output;
    }

    protected AggOperator getLocalAggOperator(AggOperator aggOperator){
        return AggOperator.copyFrom(aggOperator);
    }

    public void shutDown() {
       hBaseClient.close();
    }
}
