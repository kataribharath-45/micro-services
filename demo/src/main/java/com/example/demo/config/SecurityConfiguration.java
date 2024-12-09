package com.example.demo.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	@Autowired
	DataSource datasource;

	@Bean
	SecurityFilterChain defaultSecurityChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated());
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.headers(headers ->

		headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

		http.csrf(csrf -> csrf.disable());
		return http.build();
	}

	// InMemory Authentication
	// Basic one for testing purpose

	@Bean
	public UserDetailsService userDetailService() {

		UserDetails user1 = User.withUsername("bharath").password(passwordEncoder()
				.encode("Bharath@123")).roles("USER")
				.build();

		UserDetails user2 = User.withUsername("jeevan").password(passwordEncoder()
				.encode("Jeevan@123")).roles("USER")
				.build();

		UserDetails user3 = User.withUsername("harika")
				.password(passwordEncoder().encode("Harika@123")).roles("USER")
				.build();

		UserDetails user4 = User.withUsername("samyuktha").password(passwordEncoder()
				.encode("Samyuktha@123"))
				.roles("ADMIN").build();

		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(datasource);
		userDetailsManager.createUser(user1);
		userDetailsManager.createUser(user2);
		userDetailsManager.createUser(user3);
		userDetailsManager.createUser(user4);
		return userDetailsManager;
//		return new InMemoryUserDetailsManager(user1,user2,user3,user4);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
