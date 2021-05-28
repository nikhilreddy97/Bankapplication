package com.bank.application.bankapplication.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.bankapplication.entity.Customer;
import com.bank.application.bankapplication.service.RegistrationService;
import com.bank.application.bankapplication.util.BankAccountConstants;

@RestController
@RequestMapping("/api/customer")
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) throws Exception {

		registrationService.createCustomer(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body(BankAccountConstants.CUSTOMER_CREATED_SUCCESSFULLY);
	}

	@DeleteMapping(value = "/delete/{cifNumber}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("cifNumber") int cifNumber) throws Exception {
		String response = registrationService.deleteCustomer(cifNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping(value = "/remove/{cifNumber}")
	public ResponseEntity<String> removeCustomer(@PathVariable("cifNumber") int cifNumber) throws Exception {
		String response = registrationService.removeCustomer(cifNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
