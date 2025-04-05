package dev.coffeezombie.wasp;

import dev.coffeezombie.wasp.util.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaspApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaspApplication.class, args);
	}

}
