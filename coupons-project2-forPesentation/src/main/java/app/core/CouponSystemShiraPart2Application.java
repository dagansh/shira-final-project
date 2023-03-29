package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CouponSystemShiraPart2Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CouponSystemShiraPart2Application.class, args);
	}

}
