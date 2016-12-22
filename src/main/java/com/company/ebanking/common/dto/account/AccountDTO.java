package com.company.ebanking.common.dto.account;

import java.util.List;

import com.company.ebanking.common.dto.BasicDTO;

public class AccountDTO extends BasicDTO {

    int amount;

    List<MovementDTO> movements;

    public int getAmount() {
	return amount;
    }

    public void setAmount(int amount) {
	this.amount = amount;
    }

    public List<MovementDTO> getMovements() {
	return movements;
    }

    public void setMovements(List<MovementDTO> movements) {
	this.movements = movements;
    }

    public static class AccountDTOBuilder {

	private AccountDTO instance;

	private AccountDTOBuilder() {
	    instance = new AccountDTO();
	}

	public static AccountDTOBuilder builder() {
	    return new AccountDTOBuilder();
	}

	public AccountDTOBuilder amount(int amount) {
	    instance.setAmount(amount);
	    return this;
	}

	public AccountDTOBuilder movements(List<MovementDTO> movements) {
	    instance.setMovements(movements);
	    return this;
	}

	public AccountDTO build() {
	    return instance;
	}
    }

}
