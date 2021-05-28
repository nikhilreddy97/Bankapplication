package com.bank.application.bankapplication.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.bankapplication.dto.AccountsDTO;
import com.bank.application.bankapplication.entity.Accounts;
import com.bank.application.bankapplication.service.BankAccountService;
import com.bank.application.bankapplication.util.BankAccountConstants;

@RestController
@RequestMapping("/api/bank")
public class BankAccountController {

	private final BankAccountService bankAccountService;
	
	@Autowired
	public BankAccountController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	@Autowired
	ModelMapper modelMapper;

	@PostMapping(value = "/account/create")
	public ResponseEntity<String> createBankAccount(@RequestBody Accounts account) throws Exception {

		bankAccountService.createBankAccount(account);

		return ResponseEntity.status(HttpStatus.CREATED).body(BankAccountConstants.BANK_ACCOUNT_CREATED_SUCCESSFULLY);

	}

	@GetMapping(value = "/get/allAccounts/{cifNumber}")
	public ResponseEntity<List<AccountsDTO>> getAllAccounts(@PathVariable("cifNumber") int cifNumber) throws Exception {

		List<AccountsDTO> accountDetails = bankAccountService.getAllAccounts(cifNumber)
				.stream().map(data -> modelMapper.map(data, AccountsDTO.class)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(accountDetails);
	}

	
	@GetMapping(value = "/get/balance/{accountNumber}")
	public ResponseEntity<String> getBalance(@PathVariable("accountNumber") int accountNumber) throws Exception {
		Float accountBalance = bankAccountService.getBalance(accountNumber);
		String balanceInfo = "Account Balance Info: ".concat(accountBalance.toString());
		return ResponseEntity.status(HttpStatus.OK).body(balanceInfo);

	}
	
	@PutMapping(value="/account/remove/{accountNumber}")
	public ResponseEntity<String> removeAccount(@PathVariable("accountNumber") int accountNumber) throws Exception {
		String response = bankAccountService.removeAccount(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping(value = "/account/delete/{accountNumber}")
	public ResponseEntity<String> accountDelete(@PathVariable("accountNumber") int accountNumber) throws Exception {
		String respone = bankAccountService.accountDelete(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(respone);
	}
}
