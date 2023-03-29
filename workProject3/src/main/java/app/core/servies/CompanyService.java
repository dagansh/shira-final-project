package app.core.servies;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CustomException;
import app.core.repos.CompanyRepo;
import app.core.repos.CouponRepo;

@Service
@Transactional
public class CompanyService extends ClientService {

	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CouponRepo couponRepo;

	public boolean login(String email, String password) {
		if (companyRepo.existsByEmailAndPassword(email, password)) {
			return true;
		}
		return false;
	}

	public Coupon addCoupon(Coupon couponAdd, int companyId) throws CustomException {
		System.out.println(couponAdd);
		if (couponRepo.existsByTitleAndCompanyId(couponAdd.getTitle(), companyId)) {
			throw new CustomException("you can not add coupon with a same title to another coupon in this company");
		}

		Company company = companyRepo.findById(companyId)
				.orElseThrow(() -> new CustomException("this id of this company doesn't exist"));
		return company.addCoupon(couponAdd);
	}

	public Coupon updateCoupon(Coupon coupon) throws CustomException {

		Coupon couponFromDb = couponRepo.findById(coupon.getId())
				.orElseThrow(() -> new CustomException("this id of this coupon doesn't exist"));
		// set only allowed fields
		couponFromDb.setCategory(coupon.getCategory());
		couponFromDb.setTitle(coupon.getTitle());
		couponFromDb.setDescription(coupon.getDescription());
		couponFromDb.setStartDate(coupon.getStartDate());
		couponFromDb.setEndDate(coupon.getEndDate());
		couponFromDb.setAmount(coupon.getAmount());
		couponFromDb.setPrice(coupon.getPrice());
		couponFromDb.setImage(coupon.getImage());
		return couponRepo.save(couponFromDb);
	}

	public void deleteCoupon(int couponId) throws CustomException {
		Coupon coupon = couponRepo.findById(couponId)
				.orElseThrow(() -> new CustomException("this id of this coupon doesn't exist"));
		couponRepo.delete(coupon);
	}

	public List<Coupon> getCompanyCoupons(int companyId) throws CustomException {
		if (companyRepo.existsById(companyId)) {
			return couponRepo.findByCompanyId(companyId);
		}
		throw new CustomException("this id of this company doesn't exist");
	}

	public List<Coupon> getCompanyCoupons(Category category, int companyId) {
		return couponRepo.findByCategoryAndCompanyId(category, companyId);
	}

	public List<Coupon> getCompanyCoupons(double maxPrice, int companyId) {
		return couponRepo.findByPriceLessThanEqualAndCompanyId(maxPrice, companyId);
	}

	public Company getCompanyDetails(String email) throws CustomException {

		return companyRepo.findByEmail(email)
				.orElseThrow(() -> new CustomException("this company doesn't exist with this email"));

	}

	public Coupon getCompanyCouponById(int couponId, int companyId) throws CustomException {

		return couponRepo.findByIdAndCompanyId(couponId, companyId)
				.orElseThrow(() -> new CustomException("this id doen't exist"));

	}

}
