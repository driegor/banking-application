package com.company.ebanking.domain.auth;

import com.company.ebanking.domain.BasicEntity;

public class User extends BasicEntity {

    String userName;
    String password;

    protected User() {
    }

    public User(String userName, String password) {
	this.userName = userName;
	this.password = password;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
