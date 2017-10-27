package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.KeyWord;
import cn.edu.nju.software.entity.Word;
import cn.edu.nju.software.entity.WordFrequencyResult;

import java.util.List;

/**
 * @author dalec
 */
public interface NlpService extends AutoCloseable {
    /**
     * 获取关键词
     * @param content 文本
     * @param limit 数量
     * @return 关键词列表
     */
    List<KeyWord> getKeyWords(String content, int limit);

    /**
     * 词频统计
     * @param content 文本
     * @return 词频
     */
    List<WordFrequencyResult> getWordFrequency(String content);

    /**
     * 分词
     * @param content 文本
     * @return 分词结果
     */
    List<Word> paragraphProcess(String content);

    /**
     * 短语匹配
     * @param content 文本
     * @param pattern 模式。词性列表，以逗号分割线，"n,a"
     * @return 匹配到的短语列表
     */
    List<List<Word>> phrasePatternMatch(String content, String pattern);
}
