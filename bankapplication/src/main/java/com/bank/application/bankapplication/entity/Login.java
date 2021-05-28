package com.bank.application.bankapplication.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String password;

}
