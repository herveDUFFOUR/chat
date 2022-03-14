package com.herve.videomail;

import com.herve.videomail.user.Documents.Role;
import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Repositories.RoleRepository;
import com.herve.videomail.user.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan({"com.herve.videomail.user.RestServices"," com.herve.videomail.user.Services","com.herve.videomail.config"," com.herve.videomail.user.Documents","com.herve.videomail.SecurityService"})
@EnableMongoRepositories("com.herve.videomail.user.Repositories")
public class VideomailApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideomailApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository){

		return args -> {
			User user = userRepository.findCustomByLogin("sebastien");
			System.out.println(user);
/*
			List<Role> roles = new ArrayList<>();

			Role role = new Role();
			role.setName("ADMIN");

            roleRepository.insert(role);

			roles.add(role);

			User user1 = new User();
			user1.setLogin("herve");
			user1.setName("herve");
			user1.setPassword(new BCryptPasswordEncoder().encode("herve"));
			user1.setRoles(roles);

            userRepository.insert(user1);

 */
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
