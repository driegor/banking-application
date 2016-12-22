package com.company.ebanking.domain.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @Column(name = "USERNAME")
    String userName;

    @Column(name = "PASSWORD")
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
