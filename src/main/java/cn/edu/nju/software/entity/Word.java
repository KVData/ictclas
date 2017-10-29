package cn.edu.nju.software.entity;

import java.io.Serializable;

/**
 * @author dalec
 */
public class Word implements Serializable {
    private String content;
    private String partOfSpeech;

    public Word(String content, String partOfSpeech) {
        this.content = content;
        this.partOfSpeech = partOfSpeech;
    }

    public String getContent() {
        return content;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    @Override
    public String toString() {
        return "Word{" +
                "content='" + content + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                '}';
    }

    public boolean isNoun() {
        return getPartOfSpeech().startsWith("n");
    }
}
