package com.bank.application.bankapplication.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.bankapplication.entity.Customer;
import com.bank.application.bankapplication.repository.BankAccountRepository;
import com.bank.application.bankapplication.repository.CustomerRepository;
import com.bank.application.bankapplication.util.BankAccountConstants;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BankAccountRepository bankAccountRepository;

	@Override
	public void createCustomer(Customer customer) throws Exception {

		customer.setCustomerStatus(BankAccountConstants.ACTIVE);

		Customer customerDetails = customerRepository.save(customer);

		if (customerDetails == null) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_SAVED);
		}
	}

	@Override
	public String deleteCustomer(int cifNumber) throws Exception {

		customerRepository.deleteById(cifNumber);

		return BankAccountConstants.CUSTOMER_DELETED_SUCCESSFULLY;
	}

	@Override
	public String removeCustomer(int cifNumber) throws Exception {
		
		float amount = 0;

		Optional<Customer> customerResponse = customerRepository.findById(cifNumber);

		if (!customerResponse.isPresent()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}

		customerResponse.get().setCustomerStatus(BankAccountConstants.INACTIVE);

		customerRepository.save(customerResponse.get());
		
		bankAccountRepository.updateAllAccountsStatusToInActive(BankAccountConstants.INACTIVE, cifNumber,amount);
		

		return BankAccountConstants.CUSTOMER_REMOVED_SUCCESSFULLY;
	}
}
