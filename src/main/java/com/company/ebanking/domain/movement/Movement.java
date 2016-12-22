package com.company.ebanking.domain.movement;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.company.ebanking.common.enums.MovementType;
import com.company.ebanking.domain.BasicEntity;

@Entity
public class Movement extends BasicEntity {

    MovementType type;
    Date date;
    @NotNull(message = "Validation.Generic.NotNull")
    String concept;
    @Min(1)
    int quantity;

    protected Movement() {
    }

    public Movement(MovementType type, String concept, int quantity, Date date) {

	this.type = type;
	this.concept = concept;
	this.quantity = quantity;
	this.date = date;
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

    @Override
    public String toString() {
	return "Movement [type=" + type + ", date=" + date + ", concept=" + concept + ", quantity=" + quantity + ", id="
		+ id + "]";
    }

}
