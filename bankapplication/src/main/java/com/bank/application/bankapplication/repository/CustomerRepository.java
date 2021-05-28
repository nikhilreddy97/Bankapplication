package com.bank.application.bankapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.application.bankapplication.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findCustomerByUserIdAndPassword(Long userId, String password);

	List<Customer> findCustomerByCifNumber(int cifNumber);

}
