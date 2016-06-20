package com.yidian.wemedia.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Created by zhangbing on 16/5/4.
 */
public class HdfsUtil {
//    public static final String PATH = "hdfs://nameservice1/user/zhangbing/data/auto-settlement/doc_id_source_id";
    public static final String PATH = "hdfs://purple/user/zhangbing/test/user-profile/000000_0";
//    public static final String PATH = "file:///hadoop2-14.lg-4-e10.yidian.com:8020/user/zhangbing/test/user-profile/000000_0";
//    public static final String PATH = "hdfs://hadoop2-14.lg-4-e10.yidian.com:8020/user/zhangbing/data/source_doc_map";

    public static Configuration getConf(){
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        conf.addResource(new Path("/Users/zhangbing/conf/new-hadoop-conf/core-site.xml"));
        conf.addResource(new Path("/Users/zhangbing/conf/new-hadoop-conf/hdfs-site.xml"));
        return conf;
    }

    public static FSDataInputStream getInputStream() throws IOException, InterruptedException {
        Configuration conf = getConf();
        FileSystem fs = FileSystem.get(URI.create(PATH), conf, "zhangbing");
        FSDataInputStream in = fs.open(new Path(PATH));
        return in;
    }
}
