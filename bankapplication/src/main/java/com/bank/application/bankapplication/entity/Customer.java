package com.bank.application.bankapplication.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq", initialValue = 10000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private int cifNumber;

	@Column(unique = true, nullable = false)
	private Long userId;
	private String fullName;
	private String mobileNumber;
	private String address;
	private String password;
	private String customerStatus;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "gender")
	private Gender gender;
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<Accounts> accounts;

	@CreationTimestamp
	@Column(name = "registered_at")
	private LocalDateTime registeredAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
