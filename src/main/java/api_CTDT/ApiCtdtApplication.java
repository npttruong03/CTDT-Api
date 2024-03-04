package api_CTDT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class ApiCtdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCtdtApplication.class, args);
	}

}
