package MovieApp.MovieApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class MovieAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieAppApplication.class, args);
	}

}
