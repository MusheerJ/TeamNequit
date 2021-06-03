package com.teamnequit.Models;

public class Users
{
    private  String userName,userEmail,userPass;

    public Users(String userEmail, String userPass) {
        this.userEmail = userEmail;
        this.userPass = userPass;
    }

    public Users()
    {
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
}
