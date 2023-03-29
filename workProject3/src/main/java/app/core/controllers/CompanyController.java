package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CustomException;
import app.core.servies.CompanyService;

@CrossOrigin("*")

@RestController
@RequestMapping("/api/company")

public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/login")
	public boolean login(@RequestParam String email, @RequestParam String password) {
		return companyService.login(email, password);
	}

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/addCoupon/{companyId}")
	public Coupon addCoupon(@RequestBody Coupon couponAdd, @PathVariable int companyId) {
		try {
			return companyService.addCoupon(couponAdd, companyId);
		} catch (CustomException e) {
			System.out.println(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@PutMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/updateCoupon")
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		try {
			return companyService.updateCoupon(coupon);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/{couponId}")
	public void deleteCoupon(@PathVariable int couponId) {
		try {
			companyService.deleteCoupon(couponId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCompanyCoupons/{companyId}")
	public List<Coupon> getCompanyCoupons(@PathVariable int companyId) {
		try {
			return companyService.getCompanyCoupons(companyId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCompanyCouponsByCategory/{category}/{companyId}")
	public List<Coupon> getCompanyCoupons(@PathVariable Category category, @PathVariable int companyId) {
		return companyService.getCompanyCoupons(category, companyId);
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCompanyCouponsByPrice/{maxPrice}/{companyId}")
	public List<Coupon> getCompanyCoupons(@PathVariable double maxPrice, @PathVariable int companyId) {
		return companyService.getCompanyCoupons(maxPrice, companyId);
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCompanyDetails/{email}")
	public Company getCompanyDetails(@PathVariable String email) {
		try {
			return companyService.getCompanyDetails(email);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCompanyCoupon/{couponId}/{companyId}")
	public Coupon getCompanyCouponById(@PathVariable int couponId, @PathVariable int companyId) {
		try {
			return companyService.getCompanyCouponById(couponId, companyId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
