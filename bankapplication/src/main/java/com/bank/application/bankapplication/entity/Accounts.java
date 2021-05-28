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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Accounts implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq", initialValue = 5555)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private int accountNumber;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "account_type")
	private AccountType accountType;
	private float amount;

	@ToString.Exclude
	@ManyToOne(targetEntity = Customer.class)
	@JoinColumn(name = "cifNumber")
	private Customer customer;

	private String accountStatus;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
