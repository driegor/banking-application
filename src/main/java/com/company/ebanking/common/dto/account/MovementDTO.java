package com.company.ebanking.common.dto.account;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.company.ebanking.common.enums.MovementType;

public class MovementDTO {

    MovementType type;
    Date date;
    @NotNull(message = "Validation.Generic.NotNull")
    String concept;
    @Min(1)
    int quantity;

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

    public static class MovementDTOBuilder {

	private MovementDTO instance;

	private MovementDTOBuilder() {
	    instance = new MovementDTO();
	}

	public static MovementDTOBuilder builder() {
	    return new MovementDTOBuilder();
	}

	public MovementDTOBuilder type(MovementType type) {
	    instance.setType(type);
	    return this;
	}

	public MovementDTOBuilder date(Date date) {
	    instance.setDate(date);
	    return this;
	}

	public MovementDTOBuilder concept(String concept) {
	    instance.setConcept(concept);
	    return this;
	}

	public MovementDTOBuilder quantity(int quantity) {
	    instance.setQuantity(quantity);
	    return this;
	}

	public MovementDTO build() {
	    return instance;
	}
    }

}
