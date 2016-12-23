package com.company.ebanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.ebanking.domain.account.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    Account findByUserUserName(String userName);

}
