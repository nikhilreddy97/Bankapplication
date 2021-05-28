package com.bank.application.bankapplication.entity;

public enum AccountType {

	SAVINGS("SAVINGS"), CREDIT("CREDIT"), LOAN("LOAN");

	private String code;

	AccountType(String code) {
		this.code = code;
	}

	public String code() {
		return this.code;
	}

}