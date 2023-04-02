package com.example.demo.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new BookQuestDetailsService();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//	   http.authorizeRequests().anyRequest().permitAll();
		http.authorizeRequests()
				.antMatchers("/", "/informationBranch/{id}",
						"/customerServiceCenter", "/customerServiceCenter/", "/clearSession", "/clearSession/",
						"/sign/**", "/login/", "/book", "/book/{theme}", "/countStarRating",
						"/book/{theme}/page/{pageNum}", "/sign/check_email")
				.permitAll()
				.antMatchers("/admin/**")
				.hasAuthority("admin")
				.anyRequest()
				.authenticated();
		http.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/bookCover/**", "/css/**", "/js/**", "/webjars/**");
	}

}