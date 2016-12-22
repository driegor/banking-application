package com.company.ebanking.common.dto.account;

import java.util.List;

public class StatementDTO {

    BalanceDTO balance;

    List<MovementDTO> movements;

    public void setBalance(BalanceDTO balance) {
	this.balance = balance;
    }

    public BalanceDTO getBalance() {
	return balance;
    }

    public void setMovements(List<MovementDTO> movements) {
	this.movements = movements;
    }

    public List<MovementDTO> getMovements() {
	return movements;
    }

    public static class StatementDTOBuilder {

	private StatementDTO instance;

	private StatementDTOBuilder() {
	    instance = new StatementDTO();
	}

	public static StatementDTOBuilder builder() {
	    return new StatementDTOBuilder();
	}

	public StatementDTOBuilder movements(List<MovementDTO> movements) {
	    instance.setMovements(movements);
	    return this;
	}

	public StatementDTOBuilder balance(BalanceDTO balance) {
	    instance.setBalance(balance);
	    return this;
	}

	public StatementDTO build() {
	    return instance;
	}
    }

}
