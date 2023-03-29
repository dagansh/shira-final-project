package app.core.task;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.core.entities.Coupon;
import app.core.repos.CouponRepo;

@Component
public class CouponExpirationDailyJob {

	@Autowired
	private CouponRepo couponRepo;

	@Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1)
	public void run() {
		System.out.println("=====Thread start=====");
		if (couponRepo != null) {
			List<Coupon> ExpiredCoupons = couponRepo.findByEndDateLessThan(LocalDate.now());
			for (Coupon coupon : ExpiredCoupons) {
				couponRepo.delete(coupon);
			}
		}

	}

}
