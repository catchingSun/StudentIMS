package com.leimly.studentims.entity;

/**
 * Created by lizm on 17-12-6.
 */
public class User {
    private String userName;
    private String userPassword;
    private String isAdmin;

    public String getAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


}
