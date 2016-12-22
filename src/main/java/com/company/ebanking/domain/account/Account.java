package com.company.ebanking.domain.account;

import java.util.List;

import com.company.ebanking.domain.auth.User;
import com.company.ebanking.domain.movement.Movement;

public class Account {

    User user;
    int ammount;
    List<Movement> movements;

    protected Account() {
    }

    public Account(User user, int ammount, List<Movement> movements) {
	this.user = user;
	this.ammount = ammount;
	this.movements = movements;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public int getAmmount() {
	return ammount;
    }

    public void setAmmount(int ammount) {
	this.ammount = ammount;
    }

    public List<Movement> getMovements() {
	return movements;
    }

    public void setMovements(List<Movement> movements) {
	this.movements = movements;
    }

}
