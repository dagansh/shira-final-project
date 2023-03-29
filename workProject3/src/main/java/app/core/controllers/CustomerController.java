package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.entities.User;
import app.core.exceptions.CustomException;
import app.core.servies.CustomerService;

@CrossOrigin("*")

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/login")
	public boolean login(@RequestParam String email, @RequestParam String password) {
		return customerService.login(email, password);
	}

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/purchaseCoupon/{couponId}/{customerId}")
	public Coupon purchaseCoupon(@PathVariable int couponId, @PathVariable int customerId) {
		try {
			return customerService.purchaseCoupon(couponId, customerId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCustomerCoupons/{customerId}")
	public List<Coupon> getCustomerCoupons(@PathVariable int customerId) {
		try {
			return customerService.getCustomerCoupons(customerId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCustomerCouponsByCategory/{category}/{customerId}")
	public List<Coupon> getCustomerCoupons(@PathVariable Category category, @PathVariable int customerId) {
		try {
			return customerService.getCustomerCoupons(category, customerId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCustomerCouponsByPrice/{maxPrice}/{customerId}")
	public List<Coupon> getCustomerCoupons(@PathVariable double maxPrice, @PathVariable int customerId) {
		try {
			return customerService.getCustomerCoupons(maxPrice, customerId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCustomerDetailsById/{customerId}")
	public Customer getCustomerDetailsById(@PathVariable int customerId) {
		try {
			return customerService.getCustomerDetailsById(customerId);
		} catch (CustomException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCustomerDetails")
	public Customer getCustomerDetails(HttpServletRequest req) {
		User user = (User) req.getAttribute("user");
		try {
			return customerService.getCustomerDetailsById(user.getId());
		} catch (CustomException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getAllCoupons")
	public List<Coupon> getAllCoupons() {
		return customerService.getAllCoupons();
	}
}
