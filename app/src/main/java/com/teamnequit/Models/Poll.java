package com.teamnequit.Models;

import java.io.Serializable;

public class Poll implements Serializable {
    String pollDate,preparedBy,pollQue,pollOp1,pollOp2;

    public Poll(String pollDate, String preparedBy, String pollQue) {
        this.pollDate = pollDate;
        this.preparedBy = preparedBy;
        this.pollQue = pollQue;
    }



    public String getPollOp1() {
        return pollOp1;
    }

    public void setPollOp1(String pollOp1) {
        this.pollOp1 = pollOp1;
    }

    public String getPollOp2() {
        return pollOp2;
    }

    public void setPollOp2(String pollOp2) {
        this.pollOp2 = pollOp2;
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
