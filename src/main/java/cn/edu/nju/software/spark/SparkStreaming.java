package cn.edu.nju.software.spark;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.StreamingResult;
import cn.edu.nju.software.entity.Word;
import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;
import com.alibaba.fastjson.JSON;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.bson.Document;
import scala.Tuple2;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dalec
 */
public class SparkStreaming {
    public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf()
                .setAppName("NetworkWordCount")
                .set("spark.mongodb.output.uri", "mongodb://192.168.1.102:27017/test.result");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(20));
        WriteConfig writeConfig = WriteConfig.create(conf);

        try (NlpService service = new NlpServiceImpl()) {
            JavaDStream<String> lines = jssc.textFileStream("hdfs://192.168.59.10:9000/mytest/");
            JavaDStream<StreamingResult> words = lines.flatMap(line -> {
                Comment comment = JSON.parseObject(line, Comment.class);
                Date date = comment.getCreateTime();
                String content = comment.getContent();
                List<List<Word>> phraseList = service.phrasePatternMatch(content, "n,a");
                phraseList.addAll(service.phrasePatternMatch(content, "n,d,a"));
                List<StreamingResult> results = phraseList.stream()
                        .flatMap(Collection::stream)
                        .filter(Word::isNoun)
                        .map(word -> new StreamingResult(date, date, word.getContent()))
                        .collect(Collectors.toList());
                return results.iterator();
            });
            JavaPairDStream<StreamingResult, Integer> right = words.mapToPair(s -> new Tuple2<>(s, 1));
            JavaPairDStream<StreamingResult, Integer> rightResults = right.reduceByKey((i1, i2) -> i1 + i2);
            Date date = new Date();
            rightResults.foreachRDD(rdd -> {
                JavaRDD<Document> documents = rdd.map(result ->
                        Document.parse(String.format("{\"createTime\": \"%s\", \"word\": \"%s\", \"count\": %d}",
                                date, result._1.getWord(), result._2)));
                MongoSpark.save(documents, writeConfig);
            });
//            JavaPairDStream<String, Integer> right = words.mapToPair(s -> new Tuple2<>(s.getWord(), 1));
//            JavaDStream<Tuple2<Tuple2<String, String>, Integer>> rightResults = right.reduceByKey((i1, i2) -> i1 + i2)
//                    .map(t -> new Tuple2<>(new Tuple2<>(t._1, "right"), t._2));
//            JavaPairDStream<String, Tuple2<Date, Date>> left = words.mapToPair(s ->
//                    new Tuple2<>(s.getWord(), new Tuple2<>(s.getStartDate(), s.getEndDate())));
//            JavaDStream<Tuple2<Tuple2<String, String>, Tuple2<Date, Date>>> leftResults = left.reduceByKey((i1, i2) -> {
//                Date start = i1._1.before(i2._1) ? i1._1 : i2._1;
//                Date end = i1._2.after(i2._2) ? i1._2 : i2._2;
//                return new Tuple2<>(start, end);
//            }).map(t -> new Tuple2<>(new Tuple2<>(t._1, "left"), t._2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        jssc.start();
        jssc.awaitTermination();
    }

}