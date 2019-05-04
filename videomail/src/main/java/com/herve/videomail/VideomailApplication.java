package com.herve.videomail;

import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.herve.videomail.user.RestServices"," com.herve.videomail.user.Services"," com.herve.videomail.component","com.herve.videomail.config"," com.herve.videomail.user.Documents","com.herve.videomail.component"})
@EnableMongoRepositories("com.herve.videomail.user.Repositories")
public class VideomailApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideomailApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository repository){

		return args -> {

			User user = repository.findCustomByLogin("herve");
			System.out.println(user);


		};
	}

}
