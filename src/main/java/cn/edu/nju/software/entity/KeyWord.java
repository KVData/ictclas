package cn.edu.nju.software.entity;

/**
 * @author dalec
 */
public class KeyWord {
    private String keyWord;
    private String partOfSpeech;
    private double informationEntropyWeight;
    private int wordFrequencyWeight;

    public KeyWord(String keyWord, String partOfSpeech, double informationEntropyWeight, int wordFrequencyWeight) {
        this.keyWord = keyWord;
        this.partOfSpeech = partOfSpeech;
        this.informationEntropyWeight = informationEntropyWeight;
        this.wordFrequencyWeight = wordFrequencyWeight;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
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
                "keyWord='" + keyWord + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", informationEntropyWeight=" + informationEntropyWeight +
                ", wordFrequencyWeight=" + wordFrequencyWeight +
                '}';
    }
}
