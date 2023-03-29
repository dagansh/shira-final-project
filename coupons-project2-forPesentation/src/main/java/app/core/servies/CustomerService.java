package app.core.servies;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class CustomerService extends ClientService {

	private int customerId;

	@Autowired
	private CouponRepo couponRepo;
	@Autowired
	private CustomerRepo customerRepo;

	public boolean login(String email, String password) {
		if (customerRepo.existsByEmailAndPassword(email, password)) {
			customerId = customerRepo.findByEmailAndPassword(email, password).getId();
			return true;
		}
		return false;
	}

	public void purchaseCoupon(Coupon couponPurchase) throws CustomException {

		boolean canPurchase = true;
		Coupon coupon = couponRepo.findById(couponPurchase.getId())
				.orElseThrow(() -> new CustomException("this coupon doesn't exist"));
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomException("this customer doesn't login"));
		if (couponRepo.existsByCustomersIdAndId(customerId, coupon.getId())) {
			canPurchase = false;
			throw new CustomException("this coupon exists for this client");
		}
		if (couponPurchase.getAmount() <= 0) {
			canPurchase = false;
			throw new CustomException("the amount less than 1");
		}
		LocalDate now = LocalDate.now();
		if (couponPurchase.getEndDate() != null) {
			if (couponPurchase.getEndDate().isBefore(now)) {
				canPurchase = false;
				throw new CustomException("this coupon is expired");
			}
		}
		if (canPurchase) {
			couponPurchase.setAmount(couponPurchase.getAmount() - 1);
			couponRepo.save(couponPurchase);
			customer.addCoupon(couponPurchase);
			System.out.println("coupon Purchased");
		} else {
			throw new CustomException("coupon didn't purchase");
		}
	}

	public List<Coupon> getCustomerCoupons() throws CustomException {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findByCustomersId(customerId);
		} else {
			throw new CustomException("the customer doesn't login");
		}
	}

	public List<Coupon> getCustomerCoupons(Category category) throws CustomException {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findByCustomersIdAndCategory(customerId, category);
		} else {
			throw new CustomException("the customer doesn't login");
		}
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) throws CustomException {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findByPriceLessThanEqualAndCustomersId(maxPrice, customerId);
		} else {
			throw new CustomException("the customer doesn't login");
		}
	}

	public Customer getCustomerDetails() throws CustomException {
		return customerRepo.findById(customerId).orElseThrow(() -> new CustomException("this cusomer didn't login"));
	}

}
