package app.core;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.servies.AdminService;
import app.core.servies.CompanyService;
import app.core.servies.CustomerService;

//@Component
public class Test3 implements CommandLineRunner {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CompanyService companyService;

	@Override
	public void run(String... args) throws Exception {
		Customer customer1 = new Customer(0, "Shira", "Dagan", "Shira@gmail.com", "1234", null);
		Customer customer2 = new Customer(0, "Shira", "Dagan", "Dagan@gmail.com", "12345", null);
		Company company1 = new Company(0, "Company1", "company1@gmail.com", "1234", null);
		Coupon coupon1 = new Coupon(0, Category.SPORT, "coupon1", "coupon1", LocalDate.of(2022, 12, 01),
				LocalDate.of(2023, 02, 20), 100, 150, "Picture1", null, null);
		System.out.println(adminService.login("admin@admin.com", "admin"));
		adminService.addCustomer(customer1);
		adminService.addCustomer(customer2);
		System.out.println(customerService.login(customer1.getEmail(), customer1.getPassword()));
		adminService.addCompany(company1);
		System.out.println(companyService.login("company1@gmail.com", "1234"));
		companyService.addCoupon(coupon1);
		customerService.purchaseCoupon(coupon1);
		System.out.println(customerService.getCustomerCoupons());
		System.out.println(customerService.getCustomerCoupons(Category.SPORT));
		System.out.println(customerService.getCustomerCoupons(150));
		System.out.println(customerService.getCustomerDetails());
		// companyService.deleteCoupon(coupon1.getId());
	}

}
