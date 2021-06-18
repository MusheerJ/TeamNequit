package com.teamnequit.Models;

import java.io.Serializable;

public class Poll implements Serializable {
    String pollDate,preparedBy,pollQue;

    public Poll(String pollDate, String preparedBy, String pollQue) {
        this.pollDate = pollDate;
        this.preparedBy = preparedBy;
        this.pollQue = pollQue;
    }

    public Poll() {
    }

    public String getPollDate() {
        return pollDate;
    }

    public void setPollDate(String pollDate) {
        this.pollDate = pollDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPollQue() {
        return pollQue;
    }

    public void setPollQue(String pollQue) {
        this.pollQue = pollQue;
    }
}
