package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.UserCredentials;
import app.core.exceptions.CouponSystemException;
import app.core.exceptions.CustomException;
import app.core.servies.LoginManager;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private LoginManager loginManager;

	@PostMapping("/login")
	public String login(@RequestBody UserCredentials userCredentials) throws CustomException {
		try {
			System.out.println("entered");
			return this.loginManager.login(userCredentials.getEmail(), userCredentials.getPassword(),
					userCredentials.getClientType());
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

}
