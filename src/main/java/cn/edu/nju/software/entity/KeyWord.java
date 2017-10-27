package cn.edu.nju.software.entity;

/**
 * @author dalec
 */
public class KeyWord extends Word {
    private double informationEntropyWeight;
    private int wordFrequencyWeight;

    public KeyWord(String keyWord, String partOfSpeech, double informationEntropyWeight, int wordFrequencyWeight) {
        super(keyWord, partOfSpeech);
        this.informationEntropyWeight = informationEntropyWeight;
        this.wordFrequencyWeight = wordFrequencyWeight;
    }

    public double getInformationEntropyWeight() {
        return informationEntropyWeight;
    }

    public int getWordFrequencyWeight() {
        return wordFrequencyWeight;
    }

    @Override
    public String toString() {
        return "KeyWord{" +
                "content='" + getContent() + '\'' +
                ", partOfSpeech='" + getPartOfSpeech() + '\'' +
                ", informationEntropyWeight=" + informationEntropyWeight +
                ", wordFrequencyWeight=" + wordFrequencyWeight +
                '}';
    }
}
