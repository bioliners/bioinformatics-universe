package springconfiguration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories("biojobs")
@ComponentScan({"service*", "controller", "springconfiguration"})
@EntityScan("biojobs")
public class Application extends SpringBootServletInitializer {

	private static Class applicationClass = Application.class;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
    
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            storageService.deleteAll();
            storageService.init();
		};
	}
}