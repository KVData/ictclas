package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.KeyWord;
import cn.edu.nju.software.nlpir.NlpirMethod;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dalec
 */
public class NlpServiceImpl implements NlpService {
    @Override
    public List<KeyWord> getKeyWords(String content, int limit) {
        final String symbolBetweenKeyWords = "#";
        final String symbolBetweenProperties = "/";

        String result = NlpirMethod.NLPIR_GetKeyWords(content, 3, true);
        List<KeyWord> keyWordList = new LinkedList<>();
        for (String keyword : result.split(symbolBetweenKeyWords)) {
            String[] properties = keyword.split(symbolBetweenProperties);
            if (properties.length < 4) {
                continue;
            }
            keyWordList.add(new KeyWord(properties[0], properties[1], Double.valueOf(properties[2]), Integer.valueOf(properties[3])));
        }
        return keyWordList;
    }

    @Override
    public void close() throws Exception {
        NlpirMethod.NLPIR_Exit();
    }
}
