package com.winciak.medicFacility.config;


import com.winciak.medicFacility.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

	public SecurityConfig(UserService userService) {
		this.userService = userService;
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/", "/home","/register/**").permitAll()
						.requestMatchers("/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/user/**").hasRole("USER")
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/loginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll()
				)
				.logout(LogoutConfigurer::permitAll)
				.exceptionHandling().accessDeniedPage("/access-denied");

		return http.build();

	}

	

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder()); // bcrypt
		return auth;
	}
	  
}






