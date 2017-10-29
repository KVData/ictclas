package cn.edu.nju.software.entity;

import java.io.Serializable;

public class Comment implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
