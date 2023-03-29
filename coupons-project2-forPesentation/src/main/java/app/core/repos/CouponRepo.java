package app.core.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	boolean existsByTitleAndCompany(String couponTitle, Company CouponCompany);

	List<Coupon> findByCompanyId(int companyId);

	List<Coupon> findByCategoryAndCompanyId(Category couponCategory, int companyId);

	List<Coupon> findByPriceLessThanEqualAndCustomersId(double couponPrice, int customersId);

	List<Coupon> findByPriceLessThanEqualAndCompanyId(double couponPrice, int companyId);

	boolean existsByCustomersIdAndId(int customersId, int couponId);

	List<Coupon> findByCustomersId(int customersId);

	List<Coupon> findByCustomersIdAndCategory(int customersId, Category couponCategory);

	List<Coupon> findByEndDateLessThan(LocalDate couponEndDate);
}
