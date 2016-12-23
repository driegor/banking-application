package com.company.ebanking.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.ebanking.common.enums.MovementType;
import com.company.ebanking.domain.account.Account;
import com.company.ebanking.domain.auth.User;
import com.company.ebanking.domain.movement.Movement;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IAccountRepository repository;

    @Test
    public void testGetAccountByuserName() {
	String userName = "userName";
	this.entityManager.getEntityManager().persist(new User(userName, "123"));
	User user = this.entityManager.find(User.class, userName);
	this.entityManager.persist(new Account(user));
	Account account = repository.findByUserUserName(userName);

	assertNotNull(account);
	assertEquals(userName, account.getUser().getUserName());
	assertTrue(account.getMovements().isEmpty());
	assertEquals(0, account.getAmount());

    }

    @Test
    @Transactional
    public void testDeposit() {
	String userName = "userName";

	this.entityManager.getEntityManager().persist(new User(userName, "123"));
	User user = this.entityManager.find(User.class, userName);
	this.entityManager.persist(new Account(user));
	Account account = repository.findByUserUserName(userName);

	assertNotNull(account);
	assertEquals(userName, account.getUser().getUserName());
	assertTrue(account.getMovements().isEmpty());
	assertEquals(0, account.getAmount());

	String concept = "This is a new deposit";
	int quantity = 100;
	MovementType type = MovementType.DEPOSIT;
	Movement movement = new Movement(type, concept, quantity);
	movement.setAccount(account);
	account.getMovements().add(movement);
	account = repository.save(account);

	assertFalse(account.getMovements().isEmpty());
	assertEquals(1, account.getMovements().size());

	assertEquals(type, account.getMovements().get(0).getType());
	assertEquals(concept, account.getMovements().get(0).getConcept());
	assertEquals(quantity, account.getMovements().get(0).getQuantity());
	assertNotNull(account.getMovements().get(0).getDate());
	assertEquals(account.getAmount(), quantity);

    }

    @Test
    @Transactional
    public void testNdeposits() {
	String userName = "userName";

	this.entityManager.getEntityManager().persist(new User(userName, "123"));
	User user = this.entityManager.find(User.class, userName);
	this.entityManager.persist(new Account(user));
	Account account = repository.findByUserUserName(userName);

	assertNotNull(account);
	assertEquals(userName, account.getUser().getUserName());
	assertTrue(account.getMovements().isEmpty());
	assertEquals(0, account.getAmount());

	String concept = "This is a new deposit";
	int quantity = 100;
	MovementType type = MovementType.DEPOSIT;

	for (int i = 0; i < 5; i++) {
	    Movement movement = new Movement(type, concept, quantity);
	    movement.setAccount(account);
	    account.getMovements().add(movement);
	}
	account = repository.save(account);

	assertFalse(account.getMovements().isEmpty());
	assertEquals(5, account.getMovements().size());

	for (int i = 0; i < account.getMovements().size(); i++) {
	    assertEquals(type, account.getMovements().get(i).getType());
	    assertEquals(concept, account.getMovements().get(i).getConcept());
	    assertEquals(quantity, account.getMovements().get(i).getQuantity());
	    assertNotNull(account.getMovements().get(i).getDate());
	}
	assertEquals(account.getAmount(), 5 * quantity);

    }

    @Test
    @Transactional
    public void testWithdraw() {
	String userName = "userName";

	this.entityManager.getEntityManager().persist(new User(userName, "123"));
	User user = this.entityManager.find(User.class, userName);
	int initialAmount = 10000;
	this.entityManager.persist(new Account(user, initialAmount));
	Account account = repository.findByUserUserName(userName);

	assertNotNull(account);
	assertEquals(userName, account.getUser().getUserName());
	assertTrue(account.getMovements().isEmpty());
	assertEquals(initialAmount, account.getAmount());

	String concept = "This is a new withdraw";
	int quantity = 100;
	MovementType type = MovementType.WITHDRAW;
	Movement movement = new Movement(type, concept, quantity);
	movement.setAccount(account);
	account.getMovements().add(movement);
	account = repository.save(account);

	assertFalse(account.getMovements().isEmpty());
	assertEquals(1, account.getMovements().size());

	assertEquals(type, account.getMovements().get(0).getType());
	assertEquals(concept, account.getMovements().get(0).getConcept());
	assertEquals(quantity, account.getMovements().get(0).getQuantity());
	assertNotNull(account.getMovements().get(0).getDate());
	assertEquals(account.getAmount(), initialAmount - quantity);

    }

    @Test
    @Transactional
    public void testNwithdraws() {
	String userName = "userName";

	this.entityManager.getEntityManager().persist(new User(userName, "123"));
	User user = this.entityManager.find(User.class, userName);
	int initialAmount = 10000;

	this.entityManager.persist(new Account(user, initialAmount));
	Account account = repository.findByUserUserName(userName);

	assertNotNull(account);
	assertEquals(userName, account.getUser().getUserName());
	assertTrue(account.getMovements().isEmpty());
	assertEquals(initialAmount, account.getAmount());

	String concept = "This is a new withdraw";
	int quantity = 100;
	MovementType type = MovementType.WITHDRAW;

	for (int i = 0; i < 5; i++) {
	    Movement movement = new Movement(type, concept, quantity);
	    movement.setAccount(account);
	    account.getMovements().add(movement);
	}
	account = repository.save(account);

	assertFalse(account.getMovements().isEmpty());
	assertEquals(5, account.getMovements().size());

	for (int i = 0; i < account.getMovements().size(); i++) {
	    assertEquals(type, account.getMovements().get(i).getType());
	    assertEquals(concept, account.getMovements().get(i).getConcept());
	    assertEquals(quantity, account.getMovements().get(i).getQuantity());
	    assertNotNull(account.getMovements().get(i).getDate());
	}
	assertEquals(account.getAmount(), initialAmount - (5 * quantity));

    }
}
