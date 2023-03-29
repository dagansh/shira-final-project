package app.core.servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.auth.JwtUtil;
import app.core.entities.User;
import app.core.exceptions.CouponSystemException;
import app.core.exceptions.CustomException;

@Service
@Transactional
public class LoginManager {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ApplicationContext ctx;

	public String login(String email, String password, ClientType clientType)
			throws CustomException, CouponSystemException {
		System.out.println("email: " + email);
		System.out.println(clientType);
		if (clientType == ClientType.ADMIN) {
			if (adminService.login(email, password)) {
				User user = new User();
				user.setClientType(ClientType.ADMIN);
				user.setEmail("admin@admin.com");
				user.setPassword("admin");
				return this.jwtUtil.generateToken(user);
			}
		} else if (clientType == ClientType.COMPANY) {
			CompanyService companyService = ctx.getBean(CompanyService.class);
			if (companyService.login(email, password)) {
				User user = new User();
				user.setClientType(ClientType.COMPANY);
				user.setEmail(email);
				user.setPassword(password);
				user.setId(companyService.getCompanyDetails(email).getId());
				return this.jwtUtil.generateToken(user);
			}
		} else if (clientType == ClientType.CUSTOMER) {
			CustomerService customerService = ctx.getBean(CustomerService.class);
			if (customerService.login(email, password)) {
				User user = new User();
				user.setClientType(ClientType.CUSTOMER);
				user.setEmail(email);
				user.setPassword(password);
				user.setId(customerService.getCustomerDetails(email, password).getId());
				return this.jwtUtil.generateToken(user);
			}
		}

		throw new CouponSystemException("some details are wrong, please try again");

	}

}