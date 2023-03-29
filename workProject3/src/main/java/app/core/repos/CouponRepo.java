package app.core.repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	boolean existsByTitleAndCompanyId(String couponTitle, int CouponCompanyId);

	List<Coupon> findByCompanyId(int companyId);

	List<Coupon> findByCategoryAndCompanyId(Category couponCategory, int companyId);

	List<Coupon> findByPriceLessThanEqualAndCustomersId(double couponPrice, int customersId);

	List<Coupon> findByPriceLessThanEqualAndCompanyId(double couponPrice, int companyId);

	boolean existsByCustomersIdAndId(int customersId, int couponId);

	List<Coupon> findByCustomersId(int customersId);

	List<Coupon> findByCustomersIdAndCategory(int customersId, Category couponCategory);

	List<Coupon> findByEndDateLessThan(LocalDate couponEndDate);

	Optional<Coupon> findByIdAndCompanyId(int couponId, int companyId);
}
