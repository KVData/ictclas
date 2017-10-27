package cn.edu.nju.software;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.Word;
import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dalec
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

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
                if (i == 100) {

                    long now = System.currentTimeMillis();

                    List<List<Word>> list = service.phrasePatternMatch(content, "n,a");
                    logger.debug(System.currentTimeMillis() - now);

                    logger.info(list.stream()
                            .map(phrase -> String.join("",
                                    phrase.stream()
                                            .map(Word::getContent)
                                            .collect(Collectors.toList())))
                            .collect(Collectors.toList()));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
