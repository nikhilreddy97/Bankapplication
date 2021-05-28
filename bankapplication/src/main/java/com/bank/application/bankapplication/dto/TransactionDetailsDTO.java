package com.bank.application.bankapplication.dto;

import java.time.LocalDateTime;

import com.bank.application.bankapplication.entity.TransactionType;

import lombok.Data;

@Data
public class TransactionDetailsDTO {

	private int transactionId;
	private float amount;
	private String description;
	private int fromAccount;
	private int toAccount;
	private TransactionType transactionType;
	private LocalDateTime transactionDate;
	
}
