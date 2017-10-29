package cn.edu.nju.software.entity;

import java.io.Serializable;

/**
 * @author dalec
 */
public class WordFrequencyResult extends Word implements Serializable {
    private int count;

    public WordFrequencyResult(String word, String partOfSpeech, int count) {
        super(word, partOfSpeech);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "WordFrequencyResult{" +
                "content='" + getContent() + '\'' +
                ", partOfSpeech='" + getPartOfSpeech() + '\'' +
                ", count=" + count +
                '}';
    }
}
