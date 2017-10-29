package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.KeyWord;
import cn.edu.nju.software.entity.Word;
import cn.edu.nju.software.entity.WordFrequencyResult;
import cn.edu.nju.software.nlpir.NlpirMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author dalec
 */
public class NlpServiceImpl implements NlpService,Serializable {
    private static final Logger logger = LogManager.getLogger(NlpServiceImpl.class);

    private static final String HASH_KEY = "#";
    private static final String SLASH = "/";
    private static final String BLANK = " ";

    @Override
    public List<KeyWord> getKeyWords(String content, int limit) {
        String result = NlpirMethod.NLPIR_GetKeyWords(content, 3, true);
        List<KeyWord> list = new LinkedList<>();
        for (String keyword : result.split(HASH_KEY)) {
            String[] properties = keyword.split(SLASH);
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
        for (String word : result.split(HASH_KEY)) {
            int lastIndexOfSymbol = word.lastIndexOf(SLASH);
            int lastButOneIndexOfSymbol = word.lastIndexOf(SLASH, lastIndexOfSymbol-1);
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
    public List<Word> paragraphProcess(String content) {
        String result = NlpirMethod.NLPIR_ParagraphProcess(content, 1);
        return getWordList(result);
    }

    @Override
    public List<List<Word>> phrasePatternMatch(String content, String pattern) {
        String result = NlpirMethod.NLPIR_ParagraphProcess(content, 1);
        Pattern p = Pattern.compile(String.format("(%s)",
                String.join(" ", Arrays.stream(pattern.split(","))
                        .map(partOfSpeech -> String.format("\\p{L}*/%s\\w*", partOfSpeech))
                        .collect(Collectors.toList()))));
        logger.debug(p.toString());
        Matcher matcher = p.matcher(result);
        List<String> list = new LinkedList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list.stream()
                .map(this::getWordList)
                .collect(Collectors.toList());
    }

    @Override
    public void close() throws Exception {
        NlpirMethod.NLPIR_Exit();
    }

    private List<Word> getWordList(String phrase) {
        List<Word> list = new LinkedList<>();
        for (String word : phrase.split(BLANK)) {
            String[] properties = word.split(SLASH);
            if (properties.length < 2) {
                continue;
            }
            list.add(new Word(properties[0], properties[1]));
        }
        return list;
    }
}
