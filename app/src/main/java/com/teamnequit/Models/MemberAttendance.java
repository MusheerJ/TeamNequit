package com.teamnequit.Models;

public class MemberAttendance {
    String Name,Prn,Status,Day,Date;

    public MemberAttendance(String name, String prn, String status) {

        Name = name;
        Prn = prn;
        Status = status;
    }

    public MemberAttendance() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrn() {
        return Prn;
    }

    public void setPrn(String prn) {
        Prn = prn;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
