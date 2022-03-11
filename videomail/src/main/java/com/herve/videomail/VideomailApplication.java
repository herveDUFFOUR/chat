package com.herve.videomail;

import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan({"com.herve.videomail.user.RestServices"," com.herve.videomail.user.Services"," com.herve.videomail.component","com.herve.videomail.config"," com.herve.videomail.user.Documents","com.herve.videomail.component","com.herve.videomail.SecurityService"})
@EnableMongoRepositories("com.herve.videomail.user.Repositories")
public class VideomailApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideomailApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository repository){

		return args -> {
			User user = repository.findCustomByLogin("sebastien");
			System.out.println(user);
		};
	}

   /* @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }*/

}
