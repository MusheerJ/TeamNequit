package com.teamnequit.Models;

import java.io.Serializable;

public class MOMSheet  implements Serializable {
    String PreparedBy,Date,Time,Mom,Agenda,subPoints,DptName;

    public MOMSheet() {
    }


    public MOMSheet(String preparedBy, String date, String time, String mom, String agenda, String subPoints, String dptName) {
        PreparedBy = preparedBy;
        this.Date = date;
        this.Time = time;
        this.Mom = mom;
        this.Agenda = agenda;
        this.subPoints = subPoints;
        this.DptName = dptName;
    }

    public String getPreparedBy() {
        return PreparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        PreparedBy = preparedBy;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMom() {
        return Mom;
    }

    public void setMom(String mom) {
        Mom = mom;
    }

    public String getAgenda() {
        return Agenda;
    }

    public void setAgenda(String agenda) {
        Agenda = agenda;
    }

    public String getSubPoints() {
        return subPoints;
    }

    public void setSubPoints(String subPoints) {
        this.subPoints = subPoints;
    }

    public String getDptName() {
        return DptName;
    }

    public void setDptName(String dptName) {
        DptName = dptName;
    }

}
