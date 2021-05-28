package com.bank.application.bankapplication.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.bankapplication.entity.Accounts;
import com.bank.application.bankapplication.repository.BankAccountRepository;
import com.bank.application.bankapplication.repository.CustomerRepository;
import com.bank.application.bankapplication.repository.TransactionRepository;
import com.bank.application.bankapplication.util.BankAccountConstants;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;
	
	public void createBankAccount(Accounts account) throws Exception {

		int accountNumber = account.getAccountNumber();
		Optional<Accounts> accountresposne = bankAccountRepository.findById(accountNumber);
		if (accountresposne.isPresent()) {
			throw new Exception(BankAccountConstants.ACCOUNT_DETAILS_EXISTS);
		}
		
		account.setAccountStatus(BankAccountConstants.ACTIVE);
		Accounts accountDetails = bankAccountRepository.save(account);
		if (accountDetails == null) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_SAVED);
		}
	}

	public List<Accounts> getAllAccounts(int cifNumber) throws Exception {
		List<Accounts> accountDetails = bankAccountRepository.findAccountsByCifNumber(cifNumber).stream()
				.filter(details -> details.getAccountStatus().equals(BankAccountConstants.ACTIVE)).map(data -> {
					data.getCustomer().setCifNumber(cifNumber);
					return data;
				}).collect(Collectors.toList());

		if (accountDetails.isEmpty()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}
		System.out.println(accountDetails);
		return accountDetails;
	}

	@Override
	public Float getBalance(int accountNumber) throws Exception {

		Optional<Accounts> accountDetailsResponse = bankAccountRepository.findById(accountNumber);

		if (!accountDetailsResponse.isPresent()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}

		Float bankBalance = accountDetailsResponse.get().getAmount();

		return bankBalance;

	}

	@Override
	public String accountDelete(int accountNumber) throws Exception {

		bankAccountRepository.deleteById(accountNumber);
		
		return BankAccountConstants.BANK_ACCOUNT_REMOVED_SUCCESSFULLY;

	}

	@Override
	public String removeAccount(int accountNumber) throws Exception {

		Optional<Accounts> accountResponse = bankAccountRepository.findById(accountNumber);

		if (!accountResponse.isPresent()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}
		float amount = 0;
		bankAccountRepository.updateAccountStatusToInactive(BankAccountConstants.INACTIVE, accountNumber,amount);
		return BankAccountConstants.BANK_ACCOUNT_REMOVED_SUCCESSFULLY;
	}

}