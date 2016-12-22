package com.company.ebanking.domain.movement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.company.ebanking.common.enums.MovementType;
import com.company.ebanking.domain.BasicEntity;
import com.company.ebanking.domain.account.Account;

@Entity
public class Movement extends BasicEntity {

    @Enumerated(EnumType.STRING)
    MovementType type;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FIELD")
    Date date;

    @Column(name = "CONCEPT")
    @NotNull(message = "Validation.Generic.NotNull")
    String concept;

    @Min(1)
    @Column(name = "QUANTITY")
    int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    Account account;

    protected Movement() {
    }

    public Movement(MovementType type, String concept, int quantity) {

	this.type = type;
	this.concept = concept;
	this.quantity = quantity;
    }

    @PrePersist
    public void onPrePersist() {
	this.date = new Date();
	int amount = this.getAccount().getAmount();
	amount = MovementType.DEPOSIT.equals(type) ? amount + quantity : amount - quantity;
	this.getAccount().setAmount(amount);
    }

    public MovementType getType() {
	return type;
    }

    public void setType(MovementType type) {
	this.type = type;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public String getConcept() {
	return concept;
    }

    public void setConcept(String concept) {
	this.concept = concept;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

    @Override
    public String toString() {
	return "Movement [type=" + type + ", date=" + date + ", concept=" + concept + ", quantity=" + quantity + ", id="
		+ id + "]";
    }

}
