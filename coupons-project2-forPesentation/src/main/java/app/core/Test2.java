package app.core;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.servies.AdminService;
import app.core.servies.CompanyService;

//@Component
public class Test2 implements CommandLineRunner {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CompanyService companyService;

	@Override
	public void run(String... args) throws Exception {
		Company company1 = new Company(0, "Company1", "company1@gmail.com", "1234", null);
		Coupon coupon1 = new Coupon(0, Category.SPORT, "coupon1", "coupon1", LocalDate.of(2022, 12, 01),
				LocalDate.of(2023, 02, 20), 100, 150, "Picture1", null, null);
		adminService.addCompany(company1);
		System.out.println(companyService.login("company1@gmail.com", "1234"));
		companyService.addCoupon(coupon1);
		coupon1.setPrice(200);
		companyService.updateCoupon(coupon1);
		System.out.println(coupon1);
		// companyService.deleteCoupon(coupon1.getId());
		System.out.println(companyService.getCompanyCoupons());
		System.out.println(companyService.getCompanyCoupons(Category.SPORT));
		System.out.println(companyService.getCompanyCoupons(200.0));
		System.out.println(companyService.getCompanyDetails());
	}

}
