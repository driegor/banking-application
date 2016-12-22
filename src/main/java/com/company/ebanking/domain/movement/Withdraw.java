package com.company.ebanking.domain.movement;

import com.company.ebanking.common.enums.MovementType;

public class Withdraw extends Movement {

    public Withdraw(Movement movement) {
	super(MovementType.WITHDRAW, movement.getConcept(), movement.getQuantity());
    }
}
