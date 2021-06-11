package com.teamnequit.Models;

import java.io.Serializable;

public class Users implements Serializable
{
    private  String userName,userEmail,userPass,userPhone,userClgDpt,userNeqDpt,userBOb;

    public Users(String userEmail, String userPass) {
        this.userEmail = userEmail;
        this.userPass = userPass;
    }

    public Users()
    {
    }

    public Users(String userName, String userEmail, String userPass, String userPhone, String userClgDpt, String userNeqDpt, String userBOb) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userPhone = userPhone;
        this.userClgDpt = userClgDpt;
        this.userNeqDpt = userNeqDpt;
        this.userBOb = userBOb;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserClgDpt() {
        return userClgDpt;
    }

    public void setUserClgDpt(String userClgDpt) {
        this.userClgDpt = userClgDpt;
    }

    public String getUserNeqDpt() {
        return userNeqDpt;
    }

    public void setUserNeqDpt(String userNeqDpt) {
        this.userNeqDpt = userNeqDpt;
    }

    public String getUserBOb() {
        return userBOb;
    }

    public void setUserBOb(String userBOb) {
        this.userBOb = userBOb;
    }
}
