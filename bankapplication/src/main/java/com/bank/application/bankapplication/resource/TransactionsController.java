package com.bank.application.bankapplication.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.bankapplication.dto.AccountDTO;
import com.bank.application.bankapplication.dto.TransactionDetailsDTO;
import com.bank.application.bankapplication.entity.Transactions;
import com.bank.application.bankapplication.service.TransactionService;
import com.bank.application.bankapplication.util.BankAccountConstants;

@RestController
@RequestMapping("/api/bank")
public class TransactionsController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionsController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(value = "/add/amount")
	public ResponseEntity<String> addAmountToAccount(@RequestBody AccountDTO accountDetails) throws Exception {
		transactionService.addAmountToAccount(accountDetails);
		return ResponseEntity.status(HttpStatus.OK).body(BankAccountConstants.SUCCESS);
	}

	@PostMapping(value = "/send/amount/to")
	public ResponseEntity<String> sendAmountToAccount(@RequestBody TransactionDetailsDTO accountDetails)
			throws Exception {
		transactionService.sendAmountToAccount(accountDetails);
		return ResponseEntity.status(HttpStatus.OK).body(BankAccountConstants.SUCCESS);
	}

	@GetMapping("/get/transactions/{accountNumber}")
	public ResponseEntity<List<Transactions>> getTransactionDetailsByAccountNumber(
			@PathVariable("accountNumber") int accountNumber) throws Exception {
		List<Transactions> response = transactionService.getTransactionDetailsByAccountNumber(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/withdraw/amount")
	public ResponseEntity<String> withdrawAmount(@RequestBody AccountDTO accountDetails) throws Exception {
		transactionService.withdrawAmount(accountDetails);
		return ResponseEntity.status(HttpStatus.OK).body(BankAccountConstants.SUCCESS);
	}
}
