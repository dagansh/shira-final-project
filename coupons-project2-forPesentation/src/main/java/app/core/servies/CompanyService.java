package app.core.servies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CustomException;
import app.core.repos.CompanyRepo;
import app.core.repos.CouponRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {

	private int companyId;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CouponRepo couponRepo;

	public boolean login(String email, String password) {
		if (companyRepo.existsByEmailAndPassword(email, password)) {
			companyId = companyRepo.findByEmailAndPassword(email, password).getId();
			return true;
		}
		return false;
	}

	public void addCoupon(Coupon couponAdd) throws CustomException {
		if (couponAdd.getCompany() != null) {
			if (couponRepo.existsByTitleAndCompany(couponAdd.getTitle(), couponAdd.getCompany())) {
				throw new CustomException("you can not add coupon with a same title to another coupon in this company");

			}
		}
		Company company = companyRepo.findById(companyId)
				.orElseThrow(() -> new CustomException("this id of this company doesn't exist"));
		company.addCoupon(couponAdd);
	}

	public void updateCoupon(Coupon coupon) throws CustomException {

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
		couponRepo.save(couponFromDb);
	}

	public void deleteCoupon(int couponId) throws CustomException {
		Coupon coupon = couponRepo.findById(couponId)
				.orElseThrow(() -> new CustomException("this id of this coupon doesn't exist"));
		couponRepo.delete(coupon);
	}

	public List<Coupon> getCompanyCoupons() throws CustomException {
		if (companyRepo.existsById(companyId)) {
			return couponRepo.findByCompanyId(companyId);
		}
		throw new CustomException("this id of this company doesn't exist");
	}

	public List<Coupon> getCompanyCoupons(Category category) {
		return couponRepo.findByCategoryAndCompanyId(category, companyId);
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return couponRepo.findByPriceLessThanEqualAndCompanyId(maxPrice, companyId);
	}

	public Company getCompanyDetails() throws CustomException {

		return companyRepo.findById(companyId).orElseThrow(() -> new CustomException("this company doesn't login"));

	}

}
