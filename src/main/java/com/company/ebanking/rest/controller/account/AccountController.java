package com.company.ebanking.rest.controller.account;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.ebanking.common.dto.account.AccountDTO;
import com.company.ebanking.common.dto.account.MovementDTO;
import com.company.ebanking.common.exception.AccountException;
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
    public ResponseEntity<AccountDTO> balance(@AuthenticationPrincipal Principal principal) throws AccountException {
	AccountDTO account = accountService.getBalance(principal.getName());
	return Responses.success(account);
    }

    @RequestMapping(value = "/statement", method = RequestMethod.GET)
    public ResponseEntity<AccountDTO> statement(@AuthenticationPrincipal Principal principal) throws AccountException {
	AccountDTO accountDTO = accountService.getStatement(principal.getName());
	return Responses.success(accountDTO);
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<AccountDTO> deposit(@RequestBody @Valid MovementDTO movement,
	    @AuthenticationPrincipal Principal principal) throws AccountException {
	AccountDTO newBalance = accountService.deposit(movement, principal.getName());
	return Responses.success(newBalance);
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ResponseEntity<AccountDTO> withdraw(@RequestBody @Valid MovementDTO movement,
	    @AuthenticationPrincipal Principal principal) throws AccountException {
	AccountDTO newBalance = accountService.withdraw(movement, principal.getName());
	return Responses.success(newBalance);
    }

}
