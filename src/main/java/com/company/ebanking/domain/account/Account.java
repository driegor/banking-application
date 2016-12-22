package com.company.ebanking.domain.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import com.company.ebanking.domain.BasicEntity;
import com.company.ebanking.domain.auth.User;
import com.company.ebanking.domain.movement.Movement;

@Entity(name = "ACCOUNT")
public class Account extends BasicEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NAME")
    User user;

    @Min(0)
    @Column(name = "AMOUNT")
    int amount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = { CascadeType.MERGE,
	    CascadeType.PERSIST }, orphanRemoval = true)
    List<Movement> movements = new ArrayList<>();

    protected Account() {
    }

    public Account(User user, int amount, List<Movement> movements) {
	this.user = user;
	this.amount = amount;
	this.movements = movements;
    }

    public Account(User user, int amount) {
	this.user = user;
	this.amount = amount;
    }

    public Account(User user) {
	this.user = user;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public int getAmount() {
	return amount;
    }

    public void setAmount(int amount) {
	this.amount = amount;
    }

    public List<Movement> getMovements() {
	return movements;
    }

    public void setMovements(List<Movement> movements) {
	this.movements = movements;
    }

}
