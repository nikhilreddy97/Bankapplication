package com.bank.application.bankapplication.service;

import com.bank.application.bankapplication.entity.Customer;

public interface RegistrationService {

	void createCustomer(Customer customer) throws Exception;

	String deleteCustomer(int cifNumber) throws Exception ;

	String removeCustomer(int cifNumber) throws Exception;

}
