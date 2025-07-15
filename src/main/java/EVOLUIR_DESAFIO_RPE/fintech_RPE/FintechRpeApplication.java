package EVOLUIR_DESAFIO_RPE.fintech_RPE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FintechRpeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechRpeApplication.class, args);
	}

}
