package app.core.servies;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CustomException;
import app.core.repos.CouponRepo;
import app.core.repos.CustomerRepo;

@Service
@Transactional
public class CustomerService extends ClientService {

	@Autowired
	private CouponRepo couponRepo;
	@Autowired
	private CustomerRepo customerRepo;

	public boolean login(String email, String password) {
		if (customerRepo.existsByEmailAndPassword(email, password)) {
			return true;
		}
		return false;
	}

	public Coupon purchaseCoupon(int couponId, int customerId) throws CustomException {

		boolean canPurchase = true;
		Coupon coupon = couponRepo.findById(couponId)
				.orElseThrow(() -> new CustomException("this coupon doesn't exist"));
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomException("this customer doesn't login"));
		if (couponRepo.existsByCustomersIdAndId(customerId, coupon.getId())) {
			canPurchase = false;
			throw new CustomException("this coupon exists for this client");
		}
		if (coupon.getAmount() <= 0) {
			canPurchase = false;
			throw new CustomException("the amount less than 1");
		}
		LocalDate now = LocalDate.now();
		if (coupon.getEndDate() != null) {
			if (coupon.getEndDate().isBefore(now)) {
				canPurchase = false;
				throw new CustomException("this coupon is expired");
			}
		}
		if (canPurchase) {
			coupon.setAmount(coupon.getAmount() - 1);
			couponRepo.save(coupon);
			customer.addCoupon(coupon);
			System.out.println("coupon Purchased");
			return coupon;
		} else {
			throw new CustomException("coupon didn't purchase");
		}
	}

	public List<Coupon> getCustomerCoupons(int customerId) throws CustomException {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findByCustomersId(customerId);
		} else {
			throw new CustomException("the customer doesn't login" + customerId);
		}
	}

	public List<Coupon> getCustomerCoupons(Category category, int customerId) throws CustomException {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findByCustomersIdAndCategory(customerId, category);
		} else {
			throw new CustomException("the customer doesn't login");
		}
	}

	public List<Coupon> getCustomerCoupons(double maxPrice, int customerId) throws CustomException {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findByPriceLessThanEqualAndCustomersId(maxPrice, customerId);
		} else {
			throw new CustomException("the customer doesn't login");
		}
	}

	public Customer getCustomerDetailsById(int id) throws CustomException {
		System.out.println("====>" + id);
		return customerRepo.findById(id).orElseThrow(() -> new CustomException("this cusomer didn't login"));
	}

	public Customer getCustomerDetails(String email, String password) throws CustomException {
		Customer customer = customerRepo.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new CustomException("this customer doesn't login"));
		return customer;
	}

	public List<Coupon> getAllCoupons() {
		return couponRepo.findAll();
	}

}
