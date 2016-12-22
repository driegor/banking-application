package com.company.ebanking.service;

import com.company.ebanking.common.dto.account.AccountDTO;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.exception.AccountException;

public interface IAccountService {

    AccountDTO deposit(MovementDTO movement, String userName) throws AccountException;

    AccountDTO withdraw(MovementDTO movement, String userName) throws AccountException;

    AccountDTO getStatement(String userName) throws AccountException;

    AccountDTO getBalance(String userName) throws AccountException;
}
