package app.core;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CustomException;
import app.core.servies.AdminService;
import app.core.servies.ClientService;
import app.core.servies.ClientType;
import app.core.servies.CompanyService;
import app.core.servies.CustomerService;
import app.core.servies.LoginManager;

//@Component
public class Test implements CommandLineRunner {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private ConfigurableApplicationContext ctx;

	private final static String ANSI_RESET = "\u001B[0m";
	private final static String ANSI_BLUE = "\u001B[34m";

	@Override
	public void run(String... args) throws Exception {
		try {
			System.out.println(ANSI_BLUE + "================COUPON SYSTEM OUTPUT===============" + ANSI_RESET);
			System.out.println(ANSI_BLUE + "A=================================================" + ANSI_RESET);
			System.out.println("The program starts");
			System.out.println(ANSI_BLUE + "B=================================================" + ANSI_RESET);
			ClientService clientService = loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
			AdminService adminService = (AdminService) clientService;
			System.out.println("ADMIN LOGIN WITH EMAIL = admin@admin.com AND PASSWORD  = 1234");
			Company company1 = new Company(1, "Company1", "company1@gmail.com", "1234", null);
			System.out.println("add company: " + company1);
			adminService.addCompany(company1);
			company1.setEmail("Company1@company1.com");
			System.out.println("update company: " + company1);
			adminService.updateCompany(company1);
			System.out.println("get all companies: " + adminService.getAllCompanies());
			System.out.println("get one company id = 1 " + adminService.getOneCompany(1));
			Customer customer1 = new Customer(1, "Shira", "Dagan", "Shira@yDagan.com", "1234", null);
			System.out.println("adding customer: " + customer1);
			adminService.addCustomer(customer1);
			customer1.setEmail("Shira@gmail.com");
			System.out.println("update customer: " + customer1);
			adminService.updateCustomer(customer1);
			System.out.println("get all customers: " + adminService.getAllCustomers());
			System.out.println("get one customer: " + adminService.getOneCustomer(1));
			System.out.println("delete customer of id = " + customer1.getId());
			adminService.deleteCustomer(customer1.getId());
			System.out.println("delete comapny of id = " + company1.getId());
			adminService.deleteCompany(company1.getId());
			System.out.println(ANSI_BLUE + "C=================================================" + ANSI_RESET);
			Company company2 = new Company(2, "Company2", "company2@gmail.com", "1234", null);
			System.out.println("copany 2 added: " + company2);
			adminService.addCompany(company2);
			ClientService clientService2 = loginManager.login("company2@gmail.com", "1234", ClientType.Company);
			CompanyService companyService = (CompanyService) clientService2;
			System.out.println("COMPANY LOGIN WITH EMAIL = company2@gmail.com AND PASSWORD  = 1234");
			Coupon coupon1 = new Coupon(0, Category.SPORT, "Sport Coupon3", "Good Coupon3", LocalDate.of(2022, 1, 1),
					LocalDate.of(2023, 12, 30), 100, 100, "www.SportImage.co.il", null, null);
			Coupon coupon2 = new Coupon(0, Category.CAMPING, "camping Coupon4", "Good Coupon4",
					LocalDate.of(2022, 11, 1), LocalDate.of(2023, 12, 30), 200, 200, "www.CampingImage.co.il", null,
					null);
			companyService.addCoupon(coupon1);
			companyService.addCoupon(coupon2);
			System.out.println("the coupon that added: " + coupon1);
			System.out.println("the coupon that added: " + coupon2);
			coupon1.setPrice(150);
			coupon2.setDescription("good coupon4");
			companyService.updateCoupon(coupon1);
			companyService.updateCoupon(coupon2);
			System.out.println("the coupon1 that updated: " + coupon1);
			System.out.println("the coupon2 that updated: " + coupon2);
			System.out.println("get all coupons of the company: " + companyService.getCompanyCoupons());
			System.out.println(
					"get all coupons of the company by category: " + companyService.getCompanyCoupons(Category.SPORT));
			System.out.println("get all coupons of the company by price: " + companyService.getCompanyCoupons(150));
			System.out.println("get company details: " + companyService.getCompanyDetails());
			System.out.println("delete coupon of id = " + coupon1.getId());
			companyService.deleteCoupon(coupon1.getId());
			System.out.println(ANSI_BLUE + "D=================================================" + ANSI_RESET);
			Customer customer2 = new Customer(2, "Noga", "Dagan", "Noga@yDagan.com", "1234", null);
			Coupon coupon3 = new Coupon(0, Category.SPORT, "Sport Coupon4", "Good Coupon5", LocalDate.of(2022, 11, 1),
					LocalDate.of(2023, 12, 30), 100, 100, "www.SportImage.co.il", null, null);
			companyService.addCoupon(coupon3);
			System.out.println("the coupon that added: " + coupon3);
			System.out.println("the customer that added: " + customer2);
			adminService.addCustomer(customer2);
			ClientService clientService3 = loginManager.login("Noga@yDagan.com", "1234", ClientType.Customer);
			CustomerService customerService = (CustomerService) clientService3;
			System.out.println("CLIENT LOGIN WITH EMAIL = Noga@yDagan.com AND PASSWORD  = 1234");
			System.out.println("purchase coupon: " + coupon2 + ", " + coupon3);
			customerService.purchaseCoupon(coupon3);
			customerService.purchaseCoupon(coupon2);
			System.out.println("get customer coupons: " + customerService.getCustomerCoupons());
			System.out
					.println("get customer coupons by category: " + customerService.getCustomerCoupons(Category.SPORT));
			System.out.println("get customer coupons by price: " + customerService.getCustomerCoupons(100));
			System.out.println("get details of customer: " + customerService.getCustomerDetails());
			System.out.println(ANSI_BLUE + "E=================================================" + ANSI_RESET);
			System.out.println("The program stops");
			ctx.close();
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}

	}

}
