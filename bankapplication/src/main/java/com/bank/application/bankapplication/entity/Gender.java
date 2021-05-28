package com.bank.application.bankapplication.entity;

public enum Gender {

	MALE("M", "MALE"), FEMALE("F", "FEMALE");

	private String code;
	private String description;

	Gender(String code, String description) {
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