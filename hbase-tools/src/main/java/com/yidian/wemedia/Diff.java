package com.yidian.wemedia;

import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ByteString;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import yidian.data.etl.EncodeUtils;
import yidian.data.meta.types.StatProtos;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhangbing on 16/3/28.
 */
public class Diff{
    private final TableName testRealDocTableName = TableName.valueOf("wemedia_doc_test");
//    private final TableName testRealDocTableName = TableName.valueOf("stat_doc_manual");
    private final TableName testBatchDocTableName = TableName.valueOf("stat_doc");
    private final StatService.HBaseClient hBaseClientLUGU;
    private final StatService.HBaseClient hBaseClientSHAHE;

    private int equalSize = 0;
    private int notEqualSize = 0;

    public Diff() throws IOException {
        hBaseClientLUGU = new StatService.HBaseClient(getConfFromLUGU(), 2);
        hBaseClientSHAHE = new StatService.HBaseClient(getConfFromSHAHE(), 2);
    }

    private Configuration getConfFromSHAHE() throws IOException {
        Configuration conf = new Configuration();
        conf.set(StatService.HBaseClient.HBASE_ZOOKEEPER_CLIENT_PORT, "2181");
        conf.set(StatService.HBaseClient.HBASE_ZOOKEEPER_QUORUM,
                "103-8-77-sh-G02.yidian.com,103-8-75-sh-G02.yidian.com,103-8-76-sh-G02.yidian.com");
//        conf.set(StatService.HBaseClient.HBASE_ZOOKEEPER_QUORUM,
//                "10.103.16.33");

//        conf.set("hbase.rpc.timeout", "100");
//        conf.set("ipc.socket.timeout", "100");
//        conf.set("hbase.client.retries.number", "3");
//        conf.set("hbase.client.pause", "100");
//        conf.set("zookeeper.session.timeout", "1000");
//        conf.set("zookeeper.recovery.retry", "1");
//        conf.set("zookeeper.recovery.retry.intervalmill", "200");
        return conf;
    }

    private Configuration getConfFromLUGU() throws IOException {
        Configuration conf = new Configuration();
        conf.set(StatService.HBaseClient.HBASE_ZOOKEEPER_CLIENT_PORT, "2181");
        conf.set(StatService.HBaseClient.HBASE_ZOOKEEPER_QUORUM,
                "hadoop2-2.lg-4-e9.yidian.com,hadoop2-13.lg-4-e10.yidian.com,hadoop2-14.lg-4-e10.yidian.com");
//        conf.set(StatService.HBaseClient.HBASE_ZOOKEEPER_QUORUM,
//                "10.103.16.33");

//        conf.set("hbase.rpc.timeout", "100");
//        conf.set("ipc.socket.timeout", "100");
//        conf.set("hbase.client.retries.number", "3");
//        conf.set("hbase.client.pause", "100");
//        conf.set("zookeeper.session.timeout", "1000");
//        conf.set("zookeeper.recovery.retry", "1");
//        conf.set("zookeeper.recovery.retry.intervalmill", "200");
        return conf;
    }

    private long getSpecificTimestamp(int dayOffset, int hour){
        String day = DateTime.now().minusDays(dayOffset).toString("yyyy-MM-dd");
        return DateTime.parse(day + " 0" + hour,
                DateTimeFormat.forPattern("yyyy-MM-dd HH")).getMillis() / 1000;
    }

    public void diff(Map<String, StatProtos.StatValue> realtimeStatMap,
                     Map<String, StatProtos.StatValue> batchStatMap) throws IOException {
        for(String rowKey : realtimeStatMap.keySet()){
            if(null == batchStatMap.get(rowKey)){
//                System.out.println("Not exist: " + rowKey);
            }else if(isEqual(realtimeStatMap.get(rowKey), batchStatMap.get(rowKey))){
//                System.out.println("Equal: " + rowKey);
//                System.out.println(realtimeStatMap.get(rowKey));
                equalSize ++;
            }else {
//                System.out.println("Not Equal: " + rowKey);
//                calculate(realtimeStatMap.get(rowKey), batchStatMap.get(rowKey));
                notEqualSize ++;
            }
        }

        System.out.println(equalSize);
        System.out.println(notEqualSize);
    }

    private boolean isEqual(StatProtos.StatValue real, StatProtos.StatValue batch){

        if(real.getCmuAddcomment() == batch.getCmuAddcomment()
                && real.getCmuLike() == batch.getCmuLike()
//                && real.getCmuThumbdown() == batch.getCmuThumbdown()
//                && real.getCmuThumbup() == batch.getCmuThumbup()
//                && real.getCntAddcomment() == batch.getCntAddcomment()
                && real.getCntClick() == batch.getCntClick()
//                && real.getCntDislike() == batch.getCntDislike()
//                && real.getCntLike() == batch.getCntLike()
                && real.getCntShare() == batch.getCntShare()
//                && real.getCntThumbdown() == batch.getCntThumbdown()
//                && real.getCntThumbup() == batch.getCntThumbup()
                && real.getCntView() == batch.getCntView()){
            return true;
        }else {
            return false;
        }
    }

    private void calculate(StatProtos.StatValue real, StatProtos.StatValue batch){
        print(real.getCmuAddcomment(), batch.getCmuAddcomment(), real.getCmuAddcomment() - batch.getCmuAddcomment());
        print(real.getCmuLike(), batch.getCmuLike(), real.getCmuLike() - batch.getCmuLike());
//        print(real.getCmuThumbdown() - batch.getCmuThumbdown());
//        print(real.getCmuThumbup() - batch.getCmuThumbup());
//        print(real.getCntAddcomment() - batch.getCntAddcomment());
        print(real.getCntClick(), batch.getCntClick(), real.getCntClick() - batch.getCntClick());
//        print(real.getCntDislike() - batch.getCntDislike());
//        print(real.getCntLike() - batch.getCntLike());
        print(real.getCntShare(), batch.getCntShare(), real.getCntShare() - batch.getCntShare());
//        print(real.getCntThumbdown() - batch.getCntThumbdown());
//        print(real.getCntThumbup() - batch.getCntThumbup());
        print(real.getCntView(), batch.getCntView(), real.getCntView() - batch.getCntView());
    }

    private void print(long real, long batch, long delta){
        System.out.println(real + "\t" + batch + "\t" + delta);
    }

    public Map<String, StatProtos.StatValue> getRealtimeStatMap() throws IOException {

        ImmutableMap.Builder<String, StatProtos.StatValue> resultBuilder = ImmutableMap.builder();

        HTableInterface hTable = hBaseClientLUGU.getTable(testRealDocTableName);

//        String startRow = "10000@0AMzQ3EQ";
//        String endRow = "10004@0CgcuDJa";
//        String endRow = "10004@0Ci9cRf8";

        ResultScanner scanner = hTable.getScanner(new Scan());
//        ResultScanner scanner = hTable.getScanner(new Scan(startRow.getBytes(), endRow.getBytes()));

        for(Result result : scanner){

            String rowKey = EncodeUtils.decodeRow(result.getRow(), 0, result.getRow().length);

            for (Cell cell : result.rawCells()) {

                ByteString bs = ByteString.copyFrom(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

                StatProtos.StatValue statValue = StatProtos.StatValue.parseFrom(bs);

                resultBuilder.put(rowKey + ":" + getFamilyAndQualifier(cell), statValue);
            }
        }

        return resultBuilder.build();
    }

    public Map<String, StatProtos.StatValue> getBatchStatMap(Map<String, StatProtos.StatValue> batchStatMap) throws IOException {

        ImmutableMap.Builder<String, StatProtos.StatValue> resultBuilder = ImmutableMap.builder();

        HTableInterface hTable = hBaseClientSHAHE.getTable(testBatchDocTableName);

        for(String mapKey : batchStatMap.keySet()){

            String rowKey = mapKey.substring(0, mapKey.indexOf(":"));

            Get get = new Get(rowKey.getBytes());
            get.addColumn("f".getBytes(), mapKey.substring(mapKey.lastIndexOf(":") + 1, mapKey.length()).getBytes());

            Result result = hTable.get(get);

            for (Cell cell : result.rawCells()) {

                String familyAndQualifer = getFamilyAndQualifier(cell);

                ByteString bs = ByteString.copyFrom(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

                StatProtos.StatValue statValue = StatProtos.StatValue.parseFrom(bs);

                resultBuilder.put(rowKey + ":" + familyAndQualifer, statValue);
            }
        }

        return resultBuilder.build();
    }


    private String getFamilyAndQualifier(Cell cell){

        return Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength())
                + ":"
                + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
    }
}
