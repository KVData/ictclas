package cn.edu.nju.software.hadoop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class MakeDir {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.1.112:9000/"),conf);
        Path path = new Path("/test");
        fs.create(path);
        fs.close();
    }
}