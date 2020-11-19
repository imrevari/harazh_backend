package ua.com.harazh.oblik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("ua.com.harazh.oblik.repository")
//@EntityScan("ua.com.harazh.oblik.domain")
public class OblikApplication {

	public static void main(String[] args) {
		SpringApplication.run(OblikApplication.class, args);
	}

}
