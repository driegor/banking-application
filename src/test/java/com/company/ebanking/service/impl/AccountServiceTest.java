package com.company.ebanking.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.company.ebanking.common.dto.account.AccountDTO;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.dto.account.MovementDTO.MovementDTOBuilder;
import com.company.ebanking.common.enums.MovementType;
import com.company.ebanking.common.exception.AccountException;
import com.company.ebanking.domain.account.Account;
import com.company.ebanking.domain.auth.User;
import com.company.ebanking.domain.movement.Movement;
import com.company.ebanking.mapper.CustomDozerMapper;
import com.company.ebanking.repository.IAccountRepository;
import com.company.ebanking.rest.mockito.MockitoBaseTest;

public class AccountServiceTest extends MockitoBaseTest {

    private IAccountRepository accountRepository;
    private AccountService accountService;
    CustomDozerMapper customDozerMapper = new CustomDozerMapper(
	    Arrays.asList("dozer/global-configuration.xml", "dozer/bean-mappings.xml"));

    @Before
    public void setup() {
	accountRepository = mock(IAccountRepository.class);
	this.accountService = new AccountService(accountRepository, customDozerMapper);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetBalance() throws AccountException {
	String userName = "dummyUserName";
	String password = "xxxx";
	int max = 5;

	List<Movement> movements = IntStream.range(0, max)
		.mapToObj(i -> new Movement(MovementType.DEPOSIT, "Deposit " + i, i)).collect(Collectors.toList());
	int amount = 100;
	User user = new User(userName, password);
	Account account = new Account(user, amount, movements);

	when(accountRepository.findByUserUserName(userName)).thenReturn(account);

	AccountDTO accountDTO = accountService.getBalance(userName);
	assertNotNull(accountDTO);
	assertEquals(amount, account.getAmount());
	assertNull(accountDTO.getMovements());
    }

    @Test
    public void testGetBalanceWithoutAccount() throws AccountException {
	String userName = "dummyUserName";
	exception.expect(AccountException.class);
	exception.expectMessage(String.format("Doesn't exist an account for user %s", userName));
	accountService.getBalance(userName);
    }

    @Test
    public void testGetStatement() throws AccountException {
	String userName = "dummyUserName";
	String password = "xxxx";
	int max = 5;

	List<Movement> movements = IntStream.range(0, max)
		.mapToObj(i -> new Movement(MovementType.DEPOSIT, "Deposit " + i, i)).collect(Collectors.toList());
	int amount = 100;
	User user = new User(userName, password);
	Account account = new Account(user, amount, movements);

	when(accountRepository.findByUserUserName(userName)).thenReturn(account);

	AccountDTO accountDTO = accountService.getStatement(userName);
	assertNotNull(accountDTO);
	assertEquals(amount, account.getAmount());
	assertNotNull(accountDTO.getMovements());
	assertEquals(max, account.getMovements().size());
    }

    @Test
    public void testGetStatementWithoutAccount() throws AccountException {
	String userName = "dummyUserName";
	exception.expect(AccountException.class);
	exception.expectMessage(String.format("Doesn't exist an account for user %s", userName));
	accountService.getStatement(userName);
    }

    @Test
    public void testDeposit() throws AccountException {
	String userName = "dummyUserName";
	String password = "xxxx";
	int max = 5;

	List<Movement> movements = IntStream.range(0, max)
		.mapToObj(i -> new Movement(MovementType.DEPOSIT, "Deposit " + i, i)).collect(Collectors.toList());

	MovementDTO movementDTO = MovementDTOBuilder.builder().type(MovementType.DEPOSIT).concept("Deposit ")
		.quantity(500).date(new Date()).build();
	int amount = 100;
	User user = new User(userName, password);
	Account account = new Account(user, amount, movements);

	when(accountRepository.findByUserUserName(userName)).thenReturn(account);
	when(accountRepository.save(any(Account.class))).thenReturn(account);

	AccountDTO accountDTO = accountService.deposit(movementDTO, userName);
	assertNotNull(accountDTO);
	assertEquals(amount, account.getAmount());
	assertNull(accountDTO.getMovements());
    }

    @Test
    public void testWithdraw() throws AccountException {
	String userName = "dummyUserName";
	String password = "xxxx";
	int max = 5;

	List<Movement> movements = IntStream.range(0, max)
		.mapToObj(i -> new Movement(MovementType.DEPOSIT, "Deposit " + i, i)).collect(Collectors.toList());

	MovementDTO movementDTO = MovementDTOBuilder.builder().type(MovementType.DEPOSIT).concept("Deposit ")
		.quantity(500).date(new Date()).build();
	int amount = 1000;
	User user = new User(userName, password);
	Account account = new Account(user, amount, movements);

	when(accountRepository.findByUserUserName(userName)).thenReturn(account);
	when(accountRepository.save(any(Account.class))).thenReturn(account);

	AccountDTO accountDTO = accountService.withdraw(movementDTO, userName);
	assertNotNull(accountDTO);
	assertEquals(amount, account.getAmount());
	assertNull(accountDTO.getMovements());
    }

    @Test
    public void testWithdrawWithoutEnoughMoney() throws AccountException {
	String userName = "dummyUserName";
	String password = "xxxx";
	int max = 5;

	List<Movement> movements = IntStream.range(0, max)
		.mapToObj(i -> new Movement(MovementType.DEPOSIT, "Deposit " + i, i)).collect(Collectors.toList());

	MovementDTO movementDTO = MovementDTOBuilder.builder().type(MovementType.DEPOSIT).concept("Deposit ")
		.quantity(500).date(new Date()).build();
	int amount = 100;
	User user = new User(userName, password);
	Account account = new Account(user, amount, movements);

	when(accountRepository.findByUserUserName(userName)).thenReturn(account);

	exception.expect(AccountException.class);
	exception.expectMessage(String.format("There isn't enough money in the account to withdraw this quantity:%s",
		movementDTO.getQuantity()));
	accountService.withdraw(movementDTO, userName);

    }

    @Test
    public void testWithdrawInvalidUser() throws AccountException {
	String userName = "dummyUserName";

	MovementDTO movementDTO = MovementDTOBuilder.builder().type(MovementType.DEPOSIT).concept("Deposit ")
		.quantity(500).date(new Date()).build();

	exception.expect(AccountException.class);
	exception.expectMessage(String.format("Doesn't exist an account for user %s", userName));
	accountService.withdraw(movementDTO, userName);

    }
}
