package com.company.ebanking.rest.controller.account;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.ebanking.common.dto.account.BalanceDTO;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.dto.account.StatementDTO;
import com.company.ebanking.rest.controller.RestErrorHandlerController;
import com.company.ebanking.rest.controller.responses.Responses;
import com.company.ebanking.service.IAccountService;

@RestController
@RequestMapping(AccountController.INITIAL_ENDPOINT)
public class AccountController extends RestErrorHandlerController {

    public static final String INITIAL_ENDPOINT = "/api/account";
    private IAccountService accountService;

    public AccountController(IAccountService accountService) {
	this.accountService = accountService;
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public ResponseEntity<BalanceDTO> balance(@AuthenticationPrincipal Principal principal) {
	BalanceDTO currentBalance = accountService.getBalance(principal.getName());
	return Responses.success(currentBalance);
    }

    @RequestMapping(value = "/statement", method = RequestMethod.GET)
    public ResponseEntity<StatementDTO> statement(@AuthenticationPrincipal Principal principal) {
	StatementDTO statement = accountService.getStatement(principal.getName());
	return Responses.success(statement);
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<BalanceDTO> deposit(@RequestBody @Valid MovementDTO movement,
	    @AuthenticationPrincipal Principal principal) {
	BalanceDTO newBalance = accountService.deposit(movement, principal.getName());
	return Responses.success(newBalance);
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ResponseEntity<BalanceDTO> withdraw(@RequestBody @Valid MovementDTO movement,
	    @AuthenticationPrincipal Principal principal) {
	BalanceDTO newBalance = accountService.withdraw(movement, principal.getName());
	return Responses.success(newBalance);
    }

}
