package com.company.ebanking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.company.ebanking.config.security.CustomBasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static String REALM = "BANKING_APP_REALM";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
	auth.inMemoryAuthentication().withUser("user1").password("123456").roles("USER");
	auth.inMemoryAuthentication().withUser("user2").password("123456").roles("USER");
	auth.inMemoryAuthentication().withUser("user3").password("123456").roles("USER");
	auth.inMemoryAuthentication().withUser("user4").password("123456").roles("USER");
	auth.inMemoryAuthentication().withUser("user5").password("123456").roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable().authorizeRequests().anyRequest().fullyAuthenticated();
	http.httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
	return new CustomBasicAuthenticationEntryPoint();
    }

}