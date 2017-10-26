package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.KeyWord;
import cn.edu.nju.software.entity.WordFrequencyResult;
import cn.edu.nju.software.nlpir.NlpirMethod;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dalec
 */
public class NlpServiceImpl implements NlpService {
    private static final String SYMBOL_BETWEEN_KEY_WORDS = "#";
    private static final String SYMBOL_BETWEEN_PROPERTIES = "/";

    @Override
    public List<KeyWord> getKeyWords(String content, int limit) {
        String result = NlpirMethod.NLPIR_GetKeyWords(content, 3, true);
        List<KeyWord> list = new LinkedList<>();
        for (String keyword : result.split(SYMBOL_BETWEEN_KEY_WORDS)) {
            String[] properties = keyword.split(SYMBOL_BETWEEN_PROPERTIES);
            if (properties.length < 4) {
                continue;
            }
            list.add(new KeyWord(properties[0], properties[1], Double.valueOf(properties[2]), Integer.valueOf(properties[3])));
        }
        return list;
    }

    @Override
    public List<WordFrequencyResult> getWordFrequency(String content) {
        String result = NlpirMethod.NLPIR_WordFreqStat(content);
        List<WordFrequencyResult> list = new LinkedList<>();
        for (String word : result.split(SYMBOL_BETWEEN_KEY_WORDS)) {
            int lastIndexOfSymbol = word.lastIndexOf(SYMBOL_BETWEEN_PROPERTIES);
            int lastButOneIndexOfSymbol = word.lastIndexOf(SYMBOL_BETWEEN_PROPERTIES, lastIndexOfSymbol-1);
            if (-1 == lastButOneIndexOfSymbol) {
                continue;
            }
            String w = word.substring(0, lastButOneIndexOfSymbol);
            String part = word.substring(lastButOneIndexOfSymbol+1, lastIndexOfSymbol);
            Integer count = Integer.valueOf(word.substring(lastIndexOfSymbol+1));
            list.add(new WordFrequencyResult(w, part, count));
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        NlpirMethod.NLPIR_Exit();
    }
}
