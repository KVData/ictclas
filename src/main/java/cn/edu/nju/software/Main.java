package cn.edu.nju.software;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.Word;
import cn.edu.nju.software.entity.WordFrequencyResult;
import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;

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
                if (i == 1000) {

                    long now = System.currentTimeMillis();

                    // 根据模式找到短语
                    List<List<Word>> phraseList = service.phrasePatternMatch(content, "n,a");
                    phraseList.addAll(service.phrasePatternMatch(content, "n,d,a"));
                    // 过滤出名词并统计词频
                    List<WordFrequencyResult> nounList = service.getWordFrequency(phraseList.stream()
                            .flatMap(Collection::stream)
                            .filter(item -> item.getPartOfSpeech().startsWith("n"))
                            .map(Word::getContent)
                            .reduce("", String::concat));
                    logger.info(nounList);
                    logger.debug(System.currentTimeMillis() - now);

                    i = 0;
                    content = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
