package app.core.servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoginManager {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ApplicationContext ctx;

	public ClientService login(String email, String password, ClientType clientType) {

		if (clientType == ClientType.Administrator) {
			if (adminService.login(email, password)) {
				return adminService;
			}
		} else if (clientType == ClientType.Company) {
			CompanyService Company = ctx.getBean(CompanyService.class);
			if (Company.login(email, password)) {
				return Company;
			}
		} else if (clientType == ClientType.Customer) {
			CustomerService Customer = ctx.getBean(CustomerService.class);
			if (Customer.login(email, password)) {
				return Customer;
			}
		}
		System.out.println("return null");
		return null;
	}

}