package com.company.ebanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ebanking.domain.account.Account;

public interface IAccountRepository extends JpaRepository<Account, Long> {

    Account findByUserUserName(String userName);

}
