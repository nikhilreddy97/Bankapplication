package com.bank.application.bankapplication.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.bankapplication.entity.Customer;
import com.bank.application.bankapplication.service.LoginService;
import com.bank.application.bankapplication.util.BankAccountConstants;

@RestController
@RequestMapping("/api/customer")
public class LoginController {

	private final LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> loginCustomer(@RequestBody Customer customer) throws Exception {

		Long userId = customer.getUserId();
		String password = customer.getPassword();

		loginService.loginCustomer(userId, password);

		return ResponseEntity.status(HttpStatus.OK).body(BankAccountConstants.LOGIN_SUCCESFULL);
	}
}
