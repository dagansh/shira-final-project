package app.core.task;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class JobDemo {

	@Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1)
	public void doEvery3Seconds() {
		System.out.println("---");
	}

	@Scheduled(cron = "0 0 * * *")
	public void doEveryMidnight() {
		System.out.println("---");
	}

}
