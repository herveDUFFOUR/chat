package com.herve.videomail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*@Autowired
    protected void configure(final AuthenticationManagerBuilder authentication) throws Exception{
        authentication.inMemoryAuthentication().withUser("herve").password("vincianne").roles("USER");

    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().anyRequest().permitAll();
        http.cors().and().csrf().disable();
    }
}
