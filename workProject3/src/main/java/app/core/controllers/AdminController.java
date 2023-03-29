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

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CustomException;
import app.core.servies.AdminService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/login")
	public boolean login(@RequestParam String email, @RequestParam String password) {
		return adminService.login(email, password);
	}

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/addCompany")
	public Company addCompany(@RequestBody Company companyAdd) {
		try {
			return adminService.addCompany(companyAdd);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/updateCompany")
	public Company updateCompany(@RequestBody Company companyAdd) {
		try {
			return adminService.updateCompany(companyAdd);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/deleteCompany/{companyId}")
	public void deleteCompany(@PathVariable int companyId) {
		try {
			adminService.deleteCompany(companyId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getAllCompanies")
	public List<Company> getAllCompanies() {
		try {
			return adminService.getAllCompanies();
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getCompany/{companyId}")
	public Company getOneCompany(@PathVariable int companyId) {
		try {
			return adminService.getOneCompany(companyId);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/addCustomer")
	public Customer addCustomer(@RequestBody Customer customerAdd) {
		try {
			System.out.println(customerAdd);
			return adminService.addCustomer(customerAdd);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/updateCustomer")
	public Customer updateCustomer(@RequestBody Customer customer) {
		try {
			return adminService.updateCustomer(customer);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/deleteCustomer/{customerID}")
	public void deleteCustomer(@PathVariable int customerID) {
		try {
			adminService.deleteCustomer(customerID);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getAllCustomers")
	public List<Customer> getAllCustomers() {
		try {
			return adminService.getAllCustomers();
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(headers = { HttpHeaders.AUTHORIZATION }, path = "/getOneCustomer/{getOneCustomer}")
	public Customer getOneCustomer(@PathVariable int getOneCustomer) {
		try {
			return adminService.getOneCustomer(getOneCustomer);
		} catch (CustomException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
