package cn.edu.nju.software;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.WordFrequencyResult;
import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author dalec
 */
public class Main {
    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z]*");

    /**
     * 数据预处理，过滤特殊字符"#"，"/"
     * @param content 需要处理的数据
     * @return 处理后的数据
     */
    private static String preProcess(String content) {
        return content
                .replace("#", "")
                .replace("/", "");
    }

    /**
     * 词频统计结果处理
     * @param list 词频统计结果
     * @return 处理结果
     */
    private static List<WordFrequencyResult> postProcess(List<WordFrequencyResult> list) {
        return list.stream()
                // 取出所有名词
                .filter(item -> item.getPartOfSpeech().startsWith("n"))
                // 过滤掉英文单词
                .filter(item -> !PATTERN.matcher(item.getWord()).matches())
                .collect(Collectors.toList());
    }

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
                    List<WordFrequencyResult> list = postProcess(service.getWordFrequency(preProcess(content)));
                    System.out.println(list
                            .stream()
                            .map(WordFrequencyResult::getWord)
                            .collect(Collectors.toList()));
                    i = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
