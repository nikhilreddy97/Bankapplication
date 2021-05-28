package com.bank.application.bankapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.application.bankapplication.entity.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

	@Query(value = "select * from Transactions where account_number = ?1", nativeQuery = true)
	List<Transactions> findTransactionsByAccountNumber(int accountNumber);
}
