package cn.edu.nju.software.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/29.
 */
public class StreamResult {
    private Date date;

    private List<WordFrequencyResult> frequencyResults;

    public List<WordFrequencyResult> getFrequencyResults() {
        return frequencyResults;
    }

    public void setFrequencyResults(List<WordFrequencyResult> frequencyResults) {
        this.frequencyResults = frequencyResults;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
