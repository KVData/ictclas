package cn.edu.nju.software;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author dalec
 */
public class Main {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("comments.json"));
             NlpService service = new NlpServiceImpl()) {
            String line;
            int i = 0;
            String content = "";
            while (null != (line = reader.readLine())) {
                Comment comment = JSON.parseObject(line, Comment.class);
                content = content.concat(comment.getContent());
                i++;
                if (i == 1000) {
                    System.out.println(service.getKeyWords(content, 10));
                    i = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
