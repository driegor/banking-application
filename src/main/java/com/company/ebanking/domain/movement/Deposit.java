package com.company.ebanking.domain.movement;

import com.company.ebanking.common.enums.MovementType;

public class Deposit extends Movement {

    public Deposit(Movement movement) {
	super(MovementType.DEPOSIT, movement.getConcept(), movement.getQuantity(), movement.getDate());
    }
}
