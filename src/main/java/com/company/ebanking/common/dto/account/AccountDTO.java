package com.company.ebanking.common.dto.account;

import java.util.List;

import com.company.ebanking.common.dto.BasicDTO;

public class AccountDTO extends BasicDTO {

    int ammount;

    List<MovementDTO> movements;

    public int getAmmount() {
	return ammount;
    }

    public void setAmmount(int ammount) {
	this.ammount = ammount;
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

	public AccountDTOBuilder ammount(int ammount) {
	    instance.setAmmount(ammount);
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
