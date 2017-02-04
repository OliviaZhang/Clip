package my.project.clip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource( value = { "classpath:application.properties" } )
public class UrlShortener {
	public static void main(String[] args) {
		SpringApplication.run(UrlShortener.class, args);
	}
}
