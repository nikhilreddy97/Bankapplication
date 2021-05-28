package com.bank.application.bankapplication.service;

import java.util.List;

import com.bank.application.bankapplication.dto.AccountDTO;
import com.bank.application.bankapplication.dto.TransactionDetailsDTO;
import com.bank.application.bankapplication.entity.Transactions;

public interface TransactionService {

	void addAmountToAccount(AccountDTO accountDetails) throws Exception;

	void sendAmountToAccount(TransactionDetailsDTO accountDetails) throws Exception;

	List<Transactions> getTransactionDetailsByAccountNumber(int accountNumber) throws Exception;

	void withdrawAmount(AccountDTO accountDetails) throws Exception;

	//void saveAmount(AccountDTO accountDetails) throws Exception;

}
