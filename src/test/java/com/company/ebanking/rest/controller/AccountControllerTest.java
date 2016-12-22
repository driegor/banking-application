package com.company.ebanking.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.ebanking.common.dto.account.BalanceDTO;
import com.company.ebanking.common.dto.account.BalanceDTO.BalanceDTOBuilder;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.dto.account.MovementDTO.MovementDTOBuilder;
import com.company.ebanking.common.dto.account.StatementDTO;
import com.company.ebanking.common.dto.account.StatementDTO.StatementDTOBuilder;
import com.company.ebanking.common.enums.MovementType;
import com.company.ebanking.common.utils.MapperUtils;
import com.company.ebanking.rest.controller.account.AccountController;
import com.company.ebanking.rest.mockito.MvcControllerBaseTest;
import com.company.ebanking.service.IAccountService;

public class AccountControllerTest extends MvcControllerBaseTest {

    @Mock
    IAccountService accountService;

    @InjectMocks
    AccountController accountController;

    @Override
    protected Object getController() {
	return accountController;
    }

    @Test
    public void testGetBalance() throws Exception {

	int ammount = 10000;
	String name = "dummyName";

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};
	BalanceDTO balance = BalanceDTOBuilder.builder().ammount(ammount).build();
	Mockito.when(accountService.getBalance(name)).thenReturn(balance);

	mockMvc.perform(MockMvcRequestBuilders.get("/api/account/balance").principal(principal)).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(contentType))
		.andExpect(content().string(MapperUtils.toJsonAsTring(balance)));
    }

    @Test
    public void testGetStatement() throws Exception {

	int ammount = 10000;
	int max = 5;
	String name = "dummyName";

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};
	BalanceDTO balance = BalanceDTOBuilder.builder().ammount(ammount).build();

	List<MovementDTO> movements = IntStream.range(1, max).mapToObj(i -> MovementDTOBuilder.builder()
		.concept("Deposit " + i).date(new Date()).quantity(i).type(MovementType.DEPOSIT).build())
		.collect(Collectors.toList());

	StatementDTO statement = StatementDTOBuilder.builder().balance(balance).movements(movements).build();

	Mockito.when(accountService.getStatement(name)).thenReturn(statement);

	mockMvc.perform(MockMvcRequestBuilders.get("/api/account/statement").principal(principal)).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(contentType))
		.andExpect(content().string(MapperUtils.toJsonAsTring(statement)));
    }

    @Test
    public void testDeposit() throws Exception {

	int ammount = 10000;
	String name = "dummyName";
	String concept = "New deposit";
	int quantity = 500;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	BalanceDTO balance = BalanceDTOBuilder.builder().ammount(ammount).build();
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.concept(concept)
		.type(MovementType.DEPOSIT)
		.quantity(quantity).build();
	// @formatter:on

	Mockito.when(accountService.deposit(refEq(movement, "date"), eq(name))).thenReturn(balance);

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/deposit").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(contentType))
		.andExpect(content().string(MapperUtils.toJsonAsTring(balance)));

    }

    @Test
    public void testDepositWithoutConcept() throws Exception {

	String name = "dummyName";
	int quantity = 500;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.type(MovementType.DEPOSIT)
		.quantity(quantity).build();
	// @formatter:on

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/deposit").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.httpStatusCode", is(HttpStatus.BAD_REQUEST.value())))
		.andExpect(jsonPath("$.validationError.fieldErrors", hasSize(1)));

    }

    @Test
    public void testDepositWith0quantity() throws Exception {

	String name = "dummyName";
	String concept = "New deposit";

	int quantity = 0;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.type(MovementType.DEPOSIT)
		.concept(concept)
		.quantity(quantity).build();
	// @formatter:on

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/deposit").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.httpStatusCode", is(HttpStatus.BAD_REQUEST.value())))
		.andExpect(jsonPath("$.validationError.fieldErrors", hasSize(1)));

    }

    @Test
    public void testDepositWithNegativequantity() throws Exception {

	String name = "dummyName";
	String concept = "New deposit";

	int quantity = -1000;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.type(MovementType.DEPOSIT)
		.concept(concept)
		.quantity(quantity).build();
	// @formatter:on

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/deposit").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.httpStatusCode", is(HttpStatus.BAD_REQUEST.value())))
		.andExpect(jsonPath("$.validationError.fieldErrors", hasSize(1)));

    }

    @Test
    public void testWithDraw() throws Exception {

	int ammount = 10000;
	String name = "dummyName";
	String concept = "New WithDraw";
	int quantity = 500;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	BalanceDTO balance = BalanceDTOBuilder.builder().ammount(ammount).build();
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.concept(concept)
		.type(MovementType.DEPOSIT)
		.quantity(quantity).build();
	// @formatter:on

	Mockito.when(accountService.withdraw(refEq(movement, "date"), eq(name))).thenReturn(balance);

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/withdraw").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(contentType))
		.andExpect(content().string(MapperUtils.toJsonAsTring(balance)));

    }

    @Test
    public void testWithDrawWithoutConcept() throws Exception {

	String name = "dummyName";
	int quantity = 500;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.type(MovementType.WITHDRAW)
		.quantity(quantity).build();
	// @formatter:on

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/withdraw").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.httpStatusCode", is(HttpStatus.BAD_REQUEST.value())))
		.andExpect(jsonPath("$.validationError.fieldErrors", hasSize(1)));

    }

    @Test
    public void testWithDrawWith0quantity() throws Exception {

	String name = "dummyName";
	String concept = "New deposit";

	int quantity = 0;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.type(MovementType.WITHDRAW)
		.concept(concept)
		.quantity(quantity).build();
	// @formatter:on

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/withdraw").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.httpStatusCode", is(HttpStatus.BAD_REQUEST.value())))
		.andExpect(jsonPath("$.validationError.fieldErrors", hasSize(1)));

    }

    @Test
    public void testWithDrawWithNegativequantity() throws Exception {

	String name = "dummyName";
	String concept = "New WithDraw";

	int quantity = -1000;

	Principal principal = new Principal() {

	    @Override
	    public String getName() {
		return name;
	    }
	};

	// @formatter:off
	MovementDTO movement = MovementDTOBuilder.builder()
		.date(new Date())
		.type(MovementType.WITHDRAW)
		.concept(concept)
		.quantity(quantity).build();
	// @formatter:on

	mockMvc.perform(MockMvcRequestBuilders.post("/api/account/withdraw").contentType(contentType)
		.principal(principal).content(MapperUtils.toJsonAsBytes(movement))).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.httpStatusCode", is(HttpStatus.BAD_REQUEST.value())))
		.andExpect(jsonPath("$.validationError.fieldErrors", hasSize(1)));

    }

}
