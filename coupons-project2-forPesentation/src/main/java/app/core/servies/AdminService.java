package app.core.servies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CustomException;
import app.core.repos.CompanyRepo;
import app.core.repos.CustomerRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminService extends ClientService {

	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CustomerRepo customerRepo;

	public boolean login(String email, String password) {
		String correctEmail = "admin@admin.com";
		String correctPassword = "admin";
		if (email.equals(correctEmail) && password.equals(correctPassword)) {
			return true;
		}
		return false;
	}

	public void addCompany(Company companyAdd) throws CustomException {
		if (companyRepo.existsByName(companyAdd.getName()) || companyRepo.existsByEmail(companyAdd.getEmail())) {
			throw new CustomException(
					"you can not add a company with a name or an email that is similar to another company");
		}
		companyRepo.save(companyAdd);

	}

	public void updateCompany(Company companyAdd) throws CustomException {
		Company company = companyRepo.findById(companyAdd.getId())
				.orElseThrow(() -> new CustomException("this company doesn't exist"));
		company.setEmail(companyAdd.getEmail());
		company.setPassword(companyAdd.getPassword());
		companyRepo.save(company);
	}

	public void deleteCompany(int companyId) throws CustomException {
		if (!companyRepo.existsById(companyId)) {
			throw new CustomException("this company doesn't exist");
		}
		companyRepo.deleteById(companyId);
	}

	public List<Company> getAllCompanies() throws CustomException {
		return companyRepo.findAll();

	}

	public Company getOneCompany(int companyId) throws CustomException {
		return companyRepo.findById(companyId).orElseThrow(() -> new CustomException("The company can't be returned"));
	}

	public void addCustomer(Customer customerAdd) throws CustomException {
		if (customerRepo.existsByEmail(customerAdd.getEmail())) {
			throw new CustomException("you can not add a customer with an email that is similar to another company");
		}
		customerRepo.save(customerAdd);
	}

	public void updateCustomer(Customer customer) throws CustomException {
		if (customerRepo.existsById(customer.getId())) {
			customerRepo.save(customer);
		} else {
			throw new CustomException("this customer doesn't exist");
		}
	}

	public void deleteCustomer(int customerID) throws CustomException {
		if (customerRepo.existsById(customerID)) {
			customerRepo.deleteById(customerID);
		} else {
			throw new CustomException("this customer doesn't exist");
		}
	}

	public List<Customer> getAllCustomers() throws CustomException {
		return customerRepo.findAll();
	}

	public Customer getOneCustomer(int customerID) throws CustomException {
		return customerRepo.findById(customerID)
				.orElseThrow(() -> new CustomException("The customer was not returned"));
	}

}
