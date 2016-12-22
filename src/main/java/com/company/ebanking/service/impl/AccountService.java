package com.company.ebanking.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.company.ebanking.common.dto.account.AccountDTO;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.enums.MovementType;
import com.company.ebanking.common.exception.AccountException;
import com.company.ebanking.domain.account.Account;
import com.company.ebanking.domain.movement.Deposit;
import com.company.ebanking.domain.movement.Movement;
import com.company.ebanking.domain.movement.Withdraw;
import com.company.ebanking.mapper.CustomDozerMapper;
import com.company.ebanking.mapper.MapConst;
import com.company.ebanking.repository.IAccountRepository;
import com.company.ebanking.service.IAccountService;

public class AccountService implements IAccountService {

    private static final String DOESNT_EXIST_USER_ACCOUNT_MESSAGE = "Doesn't exist an account for user %s";
    private static final String NOT_ENOUGH_MONEY_MESSAGE = "There isn't enough money in the account to withdraw this quantity:%s";

    CustomDozerMapper mapper;
    IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository, CustomDozerMapper mapper) {
	this.mapper = mapper;
	this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO getBalance(String userName) throws AccountException {
	return getAccount(userName, null);
    }

    @Override
    public AccountDTO getStatement(String userName) throws AccountException {
	return getAccount(userName, MapConst.ACCOUNT_INCLUDE_MOVEMENTS);
    }

    private AccountDTO getAccount(String userName, String mapId) throws AccountException {
	Account account = accountRepository.findByUserUserName(userName);

	if (account == null) {
	    throw new AccountException(String.format(DOESNT_EXIST_USER_ACCOUNT_MESSAGE, userName));
	}
	return mapId != null ? mapper.map(account, AccountDTO.class, mapId) : mapper.map(account, AccountDTO.class);
    }

    @Override
    @Transactional
    public AccountDTO deposit(MovementDTO movementDTO, String userName) throws AccountException {
	Movement movement = mapper.map(movementDTO, Movement.class);
	return saveMovement(new Deposit(movement), userName);
    }

    @Override
    @Transactional
    public AccountDTO withdraw(MovementDTO movementDTO, String userName) throws AccountException {
	Movement movement = mapper.map(movementDTO, Movement.class);
	return saveMovement(new Withdraw(movement), userName);
    }

    private <M extends Movement> AccountDTO saveMovement(M movement, String userName) throws AccountException {
	Account account = accountRepository.findByUserUserName(userName);

	if (account == null) {
	    throw new AccountException(String.format(DOESNT_EXIST_USER_ACCOUNT_MESSAGE, userName));
	}

	if (MovementType.WITHDRAW.equals(movement.getType()) && movement.getQuantity() > account.getAmount()) {
	    throw new AccountException(String.format(NOT_ENOUGH_MONEY_MESSAGE, movement.getQuantity()));
	}

	account.getMovements().add(movement);
	return mapper.map(accountRepository.save(account), AccountDTO.class);
    }

}
