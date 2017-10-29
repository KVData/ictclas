package cn.edu.nju.software.spark;

/**
 * Created by Administrator on 2017/10/29.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.Word;
import cn.edu.nju.software.entity.WordFrequencyResult;
import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;
import com.alibaba.fastjson.JSON;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;
public class SparkStreaming {
    public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf().setAppName("NetworkWordCount").set("spark.testing.memory",
                "2147480000").set("spark.mongodb.output.uri", "mongodb://192.168.1.102:27017/test.result");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(20));

        try (NlpService service = new NlpServiceImpl()) {
            JavaDStream<String> lines=jssc.textFileStream("hdfs://192.168.59.10:9000/mytest/");
            JavaDStream<List<WordFrequencyResult>> results = lines.map(line ->{
                Comment comment = JSON.parseObject(line, Comment.class);
                String content = comment.getContent();
                List<List<Word>> phraseList = service.phrasePatternMatch(content, "n,a");
                phraseList.addAll(service.phrasePatternMatch(content, "n,d,a"));
                // 过滤出名词并统计词频
                return service.getWordFrequency(phraseList.stream()
                        .flatMap(Collection::stream)
                        .filter(Word::isNoun)
                        .map(Word::getContent)
                        .reduce("", String::concat));
            } );
            results.print();
        } catch (Exception e) {
            e.printStackTrace();
        }

        jssc.start();
        jssc.awaitTermination();

    }

}