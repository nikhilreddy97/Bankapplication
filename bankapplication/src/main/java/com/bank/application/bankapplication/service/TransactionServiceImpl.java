package com.bank.application.bankapplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.bankapplication.dto.AccountDTO;
import com.bank.application.bankapplication.dto.TransactionDetailsDTO;
import com.bank.application.bankapplication.entity.Accounts;
import com.bank.application.bankapplication.entity.TransactionType;
import com.bank.application.bankapplication.entity.Transactions;
import com.bank.application.bankapplication.repository.BankAccountRepository;
import com.bank.application.bankapplication.repository.TransactionRepository;
import com.bank.application.bankapplication.util.BankAccountConstants;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private List<Transactions> transactionDetailList = new ArrayList<>();

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	public void addAmountToAccount(AccountDTO accountDetails) throws Exception {
		Transactions transactionDetails = new Transactions();
		int accountNumber = accountDetails.getAccountNumber();
		float amount = 0f;
		Optional<Accounts> accountDetailsResponse = bankAccountRepository.findById(accountNumber);
		validationForToAccount(accountDetailsResponse);
		if (accountDetails.getAmount() > 0.0) {
			amount = accountDetailsResponse.get().getAmount() + accountDetails.getAmount();
		} else {
			throw new Exception(BankAccountConstants.AMOUNT_SHOULD_BE_GREATER_THAN_ZERO);
		}
		bankAccountRepository.updateAccountBalance(amount, accountNumber);

		transactionDetails.setAmount(accountDetails.getAmount());
		transactionDetails.setAccountNumber(accountNumber);
		transactionDetails.setDescription(BankAccountConstants.CASH_DEPOSIT);
		transactionDetails.setTransactionType(TransactionType.CREDITED);
		transactionDetails.setTotalAmount(amount);
		transactionRepository.save(transactionDetails);
	}

	@Override
	public void withdrawAmount(AccountDTO accountDetails) throws Exception {
		Transactions transactionDetails = new Transactions();
		int accountNumber = accountDetails.getAccountNumber();
		float withDrawAmount = 0f;
		Optional<Accounts> accountDetailsResponse = bankAccountRepository.findById(accountNumber);
		validationForToAccount(accountDetailsResponse);
		if (accountDetails.getAmount() > 0.0) {
			withDrawAmount = accountDetailsResponse.get().getAmount() - accountDetails.getAmount();
		} else {
			throw new Exception(BankAccountConstants.AMOUNT_SHOULD_BE_GREATER_THAN_ZERO);
		}
		bankAccountRepository.updateAccountBalance(withDrawAmount, accountNumber);

		transactionDetails.setAmount(accountDetails.getAmount());
		transactionDetails.setAccountNumber(accountNumber);
		transactionDetails.setDescription(BankAccountConstants.AMOUNT_WITHDRAWN);
		transactionDetails.setTransactionType(TransactionType.DEBITED);
		transactionDetails.setTotalAmount(withDrawAmount);
		transactionRepository.save(transactionDetails);

	}

	@Override
	public void sendAmountToAccount(TransactionDetailsDTO accountDetails) throws Exception {
		float amount = accountDetails.getAmount();
		int fromAccount = accountDetails.getFromAccount();
		Optional<Accounts> fromAccountReposne = bankAccountRepository.findById(fromAccount);

		Boolean fromAccountResult = validationForFromAccount(fromAccountReposne, amount);
		if (!fromAccountResult) {
			throw new Exception(BankAccountConstants.INSUFFICIENT_FUNDS);
		}

		int toAccount = accountDetails.getToAccount();
		Optional<Accounts> toAccountReposne = bankAccountRepository.findById(toAccount);
		validationForToAccount(toAccountReposne);

		float finalAmountAfterDeducted = fromAccountReposne.get().getAmount() - accountDetails.getAmount();

		bankAccountRepository.updateAccountBalance(finalAmountAfterDeducted, fromAccount);

		float finalAmountAfterCredited = toAccountReposne.get().getAmount() + accountDetails.getAmount();
		bankAccountRepository.updateAccountBalance(finalAmountAfterCredited, toAccount);

		Transactions debitedTransactionDetails = setTransactionDetailsForDebitedAccount(amount, fromAccount, toAccount,
				finalAmountAfterDeducted);
		Transactions creditedTransactionDetails = setTransactionDetailsForCreditedAccount(amount, toAccount,
				fromAccount, finalAmountAfterCredited);
		transactionDetailList.add(debitedTransactionDetails);
		transactionDetailList.add(creditedTransactionDetails);
		transactionRepository.saveAll(transactionDetailList);
	}

	private Transactions setTransactionDetailsForCreditedAccount(float amount, int toAccount, int fromAccount,
			float finalAmountAfterCredited) {
		Transactions creditedTransactionDetails = new Transactions();

		creditedTransactionDetails.setAmount(amount);
		creditedTransactionDetails.setAccountNumber(toAccount);
		creditedTransactionDetails.setDescription(
				BankAccountConstants.TRANSACTION_DESCRIPTION_CREDITED.concat(String.valueOf(fromAccount)));
		creditedTransactionDetails.setTransactionType(TransactionType.CREDITED);
		creditedTransactionDetails.setTotalAmount(finalAmountAfterCredited);
		return creditedTransactionDetails;
	}

	private Transactions setTransactionDetailsForDebitedAccount(float amount, int fromAccount, int toAccount,
			float finalAmountAfterDeducted) {
		Transactions debitedTransactionDetails = new Transactions();
		debitedTransactionDetails.setAmount(amount);
		debitedTransactionDetails.setAccountNumber(fromAccount);
		debitedTransactionDetails
				.setDescription(BankAccountConstants.TRANSACTION_DESCRIPTION_DEBITED.concat(String.valueOf(toAccount)));
		debitedTransactionDetails.setTransactionType(TransactionType.DEBITED);
		debitedTransactionDetails.setTotalAmount(finalAmountAfterDeducted);
		System.out.println(debitedTransactionDetails);
		return debitedTransactionDetails;

	}

	@Override
	public List<Transactions> getTransactionDetailsByAccountNumber(int accountNumber) throws Exception {
		List<Transactions> transactionResponse = transactionRepository.findTransactionsByAccountNumber(accountNumber);

		if (transactionResponse.isEmpty()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}

		return transactionResponse;
	}

	private boolean validationForFromAccount(Optional<Accounts> fromAccountReposne, float amount) throws Exception {
		if (!fromAccountReposne.isPresent()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}
		if (fromAccountReposne.get().getAccountStatus().equals(BankAccountConstants.INACTIVE)) {
			throw new Exception(BankAccountConstants.ACCOUNT_STATUS_INACTIVE);
		}

		boolean result = (fromAccountReposne.get().getAmount() > amount) ? Boolean.TRUE : Boolean.FALSE;
		return result;
	}

	private void validationForToAccount(Optional<Accounts> toAccountReposne) throws Exception {
		if (!toAccountReposne.isPresent()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}
		if (toAccountReposne.get().getAccountStatus().equals(BankAccountConstants.INACTIVE)) {
			throw new Exception(BankAccountConstants.ACCOUNT_STATUS_INACTIVE);
		}
	}
}

//	@Override
//	public void saveAmount(AccountDTO accountDetails) throws Exception {
//		// TODO Auto-generated method stub
//		Transactions transactionDetails = new Transactions();
//		int accountNumber = accountDetails.getAccountNumber();
//		float amount = 0f;
//		Optional<Accounts> accountDetailsResponse = bankAccountRepository.findById(accountNumber);
//		validationForToAccount(accountDetailsResponse);
//		if (accountDetails.getAmount() > 0.0) {
//			amount = accountDetailsResponse.get().getAmount() + accountDetails.getAmount();
//		} else {
//			throw new Exception(BankAccountConstants.AMOUNT_SHOULD_BE_GREATER_THAN_ZERO);
//		}
//		bankAccountRepository.updateAccountBalance(amount, accountNumber);
//
//		transactionDetails.setAmount(accountDetails.getAmount());
//		transactionDetails.setAccountNumber(accountNumber);
//		transactionDetails.setDescription(BankAccountConstants.CASH_DEPOSIT);
//		transactionDetails.setTransactionType(TransactionType.CREDITED);
//		transactionDetails.setTotalAmount(amount);
//		transactionRepository.save(transactionDetails);
//		
//		
//	}
//
//}
