package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.KeyWord;
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
    List<WordFrequencyResult> getWordFrequency(String content);
}
