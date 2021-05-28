package com.bank.application.bankapplication.dto;

import com.bank.application.bankapplication.entity.AccountType;

import lombok.Data;

@Data
public class AccountsDTO {

	private int accountNumber;
	private AccountType accountType;
	private float amount;
	private int cifNumber;
	
}
