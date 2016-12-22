package com.company.ebanking.service;

import com.company.ebanking.common.dto.account.BalanceDTO;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.dto.account.StatementDTO;

public interface IAccountService {

    BalanceDTO getBalance(String activeUser);

    BalanceDTO deposit(MovementDTO movement, String name);

    BalanceDTO withdraw(MovementDTO movement, String name);

    StatementDTO getStatement(String name);

}
