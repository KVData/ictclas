package cn.edu.nju.software.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String content;
    private Date createTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
