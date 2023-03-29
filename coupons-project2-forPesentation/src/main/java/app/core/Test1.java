package app.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.servies.AdminService;

//@Component
public class Test1 implements CommandLineRunner {

	@Autowired
	private AdminService adminService;

	@Override
	public void run(String... args) throws Exception {
		Company company1 = new Company(0, "Company1", "company1@gmail.com", "1234", null);
		Company company2 = new Company(0, "Company2", "company2@gmail.com", "1234", null);
		Customer customer1 = new Customer(0, "Shira", "Dagan", "Shira@gmail.com", "1234", null);
		Customer customer2 = new Customer(0, "Shira", "Dagan", "Dagan@gmail.com", "1234", null);
		System.out.println(adminService.login("admin@admin.com", "admin"));
		adminService.addCompany(company1);
		adminService.addCompany(company2);
		System.out.println(company1);
		System.out.println(company2);
		company1.setEmail("Dagansh011@gmail.com");
		adminService.updateCompany(company1);
		System.out.println(company1);
		adminService.deleteCompany(company1.getId());
		System.out.println("company1 deleted");
		System.out.println(adminService.getAllCompanies());
		adminService.addCustomer(customer1);
		adminService.addCustomer(customer2);
		System.out.println(customer1);
		customer1.setFirstName("Shirly");
		adminService.updateCustomer(customer1);
		System.out.println(customer1);
		adminService.deleteCustomer(customer1.getId());
		System.out.println("customer1 deleted");
		System.out.println(adminService.getAllCustomers());
		System.out.println(adminService.getOneCustomer(2));

	}

}
