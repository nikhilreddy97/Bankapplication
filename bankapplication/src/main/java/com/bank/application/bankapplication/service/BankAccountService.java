package com.bank.application.bankapplication.service;

import java.util.List;

import com.bank.application.bankapplication.entity.Accounts;

public interface BankAccountService {

	void createBankAccount(Accounts account) throws Exception;

	List<Accounts> getAllAccounts(int cifNumber) throws Exception;

	Float getBalance(int accountNumber) throws Exception;

	String accountDelete(int accountNumber) throws Exception;

	String removeAccount(int accountNumber) throws Exception;


}
