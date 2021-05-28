package com.bank.application.bankapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.application.bankapplication.entity.Accounts;

@Repository
public interface BankAccountRepository extends JpaRepository<Accounts, Integer> {

	@Modifying
	@Query("update Accounts s SET s.amount = :amount where s.accountNumber = :accountNumber")
	void updateAccountBalance(@Param("amount") float amount, @Param("accountNumber") int accountNumber);
//	@Query(value=" update Accounts SET amount =?1 where account_number=?2", nativeQuery=true)
//	void updateAccountBalance( float amount, int accountnumber);
	

	
    
	@Query(value = "Select * from Accounts a where a.cif_number = ?1", nativeQuery = true)
	List<Accounts> findAccountsByCifNumber(int cifNumber);

	@Modifying
	@Query(value = "update Accounts SET account_status = :inActive, amount = :amount where cif_number = :cifNumber", nativeQuery = true)
	void updateAllAccountsStatusToInActive(String inActive, int cifNumber, float amount);

	@Modifying
	@Query("update Accounts s SET s.accountStatus = :inActive, s.amount = :amount where s.accountNumber = :accountNumber")
	void updateAccountStatusToInactive(@Param("inActive") String inActive, @Param("accountNumber") int accountNumber, float amount);

}
