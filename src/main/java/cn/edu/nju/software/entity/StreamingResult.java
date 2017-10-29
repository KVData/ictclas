package cn.edu.nju.software.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/29.
 */
public class StreamingResult implements Serializable {
    private Date startDate;
    private Date endDate;
    private String word;

    public StreamingResult(Date startDate, Date endDate, String word) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.word = word;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreamingResult result = (StreamingResult) o;

        return word != null ? word.equals(result.word) : result.word == null;
    }

    @Override
    public int hashCode() {
        return word != null ? word.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StreamingResult{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", word='" + word +
                '}';
    }
}
