package cn.edu.nju.software.entity;

/**
 * @author dalec
 */
public class WordFrequencyResult {
    private String word;
    private String partOfSpeech;
    private int count;

    public WordFrequencyResult(String word, String partOfSpeech, int count) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "WordFrequencyResult{" +
                "word='" + word + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", count=" + count +
                '}';
    }
}
