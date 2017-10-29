package cn.edu.nju.software.mongo;

/**
 * Created by Administrator on 2017/10/29.
 */
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

import cn.edu.nju.software.entity.WordFrequencyResult;
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.sun.org.apache.xml.internal.security.encryption.DocumentSerializer;
import org.bson.Document;

public class Main {

    /**
     * @param args
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        // TODO Auto-generated method stub
//        rootpath = args[0];
//        hdfsPath = args[1];
//        path = rootpath+System.currentTimeMillis()+".txt";
        MongoClient m =null;
        try {
            int timeout = 30000;
            MongoClientOptions ops = MongoClientOptions.builder().maxConnectionLifeTime(timeout).build();
            ServerAddress s = new ServerAddress("192.168.1.102 ",27017);
                    m = new MongoClient(s,ops);
        }catch(Exception e) {
            e.printStackTrace();
        }
        MongoDatabase db = m.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("comments");
        for (Document x: collection.find().limit(10)) {
            System.out.println(x.toJson());
        }
        List<WordFrequencyResult> list = new LinkedList<>();
        List<Document> results = list.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("content", result.getContent());
            map.put("count", result.getCount());
            return new Document(map);
        }).collect(Collectors.toList());
        collection.insertMany(results);
    }

}