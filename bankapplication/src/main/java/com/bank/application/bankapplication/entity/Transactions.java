package com.bank.application.bankapplication.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Transactions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq", initialValue = 8000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private int transactionId;
	private int accountNumber;
	private float amount;
	private float totalAmount;
	private String description;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "transactionType")
	private TransactionType transactionType;

	@CreationTimestamp
	@Column(name = "transactionDate")
	private LocalDateTime transactionDate;
	/*
	 * @JoinColumn Accounts account;
	 */
}
