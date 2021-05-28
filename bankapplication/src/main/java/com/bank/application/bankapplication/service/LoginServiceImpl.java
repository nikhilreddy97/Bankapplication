package com.bank.application.bankapplication.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.bankapplication.entity.Customer;
import com.bank.application.bankapplication.repository.CustomerRepository;
import com.bank.application.bankapplication.util.BankAccountConstants;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void loginCustomer(Long userId, String password) throws Exception {

		Optional<Customer> customerDetails = customerRepository.findCustomerByUserIdAndPassword(userId,password);
		
		if(!customerDetails.isPresent()) {
			throw new Exception(BankAccountConstants.DETAILS_NOT_FOUND);
		}
	}


}
