package controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import storage.StorageProperties;
import storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@ComponentScan({"storage", "controller"})
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