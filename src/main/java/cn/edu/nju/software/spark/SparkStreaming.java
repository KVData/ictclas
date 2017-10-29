package cn.edu.nju.software.spark;

/**
 * Created by Administrator on 2017/10/29.
 */
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;
public class SparkStreaming {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("NetworkWordCount").set("spark.testing.memory",
                "2147480000");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        System.out.println(jssc);
        //创建监听文件流
        JavaDStream<String> lines=jssc.textFileStream("hdfs://192.168.1.112:9000/mytest/");

        JavaDStream<String> words = lines.flatMap((FlatMapFunction<String, String>) x -> {
            System.out.println(Arrays.asList(x.split(" ")).get(0));
            return Arrays.asList(x.split(" ")).iterator();
        });

        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) {
                return new Tuple2<String, Integer>(s, 1);
            }
        });

        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });

        wordCounts.print();

        wordCounts.dstream().saveAsTextFiles("hdfs://192.168.1.112:9000/mytest/", "spark");

        jssc.start();
        jssc.awaitTermination();



    }

}