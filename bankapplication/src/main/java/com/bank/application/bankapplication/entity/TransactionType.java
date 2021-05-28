package com.bank.application.bankapplication.entity;

public enum TransactionType {

	DEBITED("DR", "DEBITED"), CREDITED("CR", "CREDITED");

	private String code;
	private String description;

	TransactionType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String code() {
		return this.code;
	}

	public String description() {
		return this.description;
	}

}
