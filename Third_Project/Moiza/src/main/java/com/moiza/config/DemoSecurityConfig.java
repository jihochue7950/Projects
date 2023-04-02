package com.moiza.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록, 웹보안을 활성화함
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// 로그인 정보를 작성하면 WebSecurityConfigurerAdapter에서 알아서 처리함

	@Autowired
	private DataSource myDataSource;

	@Override // 세부 설정을 위한 오버라이딩
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		UserBuilder user = User.withDefaultPasswordEncoder();
//		auth.inMemoryAuthentication()
//				.withUser(user.username("test").password("123").roles("EMPLOYEE", "MANAGER", "ADMIN"))
//				.withUser(user.username("john").password("test123").roles("EMPLOYEE"))
//				.withUser(user.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
//				.withUser(user.username("susan").password("test123").roles("EMPLOYEE", "ADMIN"));

		auth.jdbcAuthentication().dataSource(myDataSource);
		/*
		 * .usersByUsernameQuery(
		 * "SELECT user_id, user_password, 'true' as enabled FROM user WHERE user_id=?")
		 * .authoritiesByUsernameQuery(
		 * "SELECT user_id, authority FROM user WHERE user_id=?");
		 */
	}

	/*
	 * roles 1. 접근성 Access Restrict 2. 가시성 Visibility
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 권환이 있는 요청 // 요청에 대한 권한 지정. Security 처리에 HttpServletRequest를 이용한다는 것을 의미한다.
				.antMatchers("/img/**").permitAll()
				.antMatchers("/css/**").permitAll() // css 리소스 폴더에 접근 가능하게 만듬
													// 한줄 위에 있어야 로그인 전에 전적용할수 있음
													// antMatchers() : 특정 경로를 지정합니다.
													// 보통 뒤에 다른 메서드가 붙습니다.
				// .anyRequest().authenticated() //모든사람이 접근했을때, 어떤 요청에도 보안을 받도록 인가정책 설정.
				// 로그인 하지 않고 모두 권한을 가짐.
				// anyRequest() : 설정한 경로 외에 모든 경로를 뜻합니다.
				// authenticated() : 인증된 사용자만이 접근할 수 있습니다.
				.antMatchers("/").permitAll() // 특정url 인증없이 접근가능 // permitAll() : 어떤 사용자든지 접근할 수 있습니다.
				// hasRole() : 특정 ROLE을 가지고 있는 사람이 접근할 수 있습니다.
				.antMatchers("/leaders/**").hasRole("MANAGER")
				.antMatchers("/systems/**").hasRole("ADMIN")
			.and()
				.formLogin() // form-login 인증 정책 //formLogin() : form 기반의 로그인을 할 수 있습니다.
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser") // 파라미터값을 받아옴/이곳으로 넘여야함
				.permitAll();
		http.logout().logoutSuccessUrl("/").permitAll();
		// 커트에러 페이지 생성하기
		http.exceptionHandling().accessDeniedPage("/access-denied");
		// exceptionHandling() : 예외사항을 설정한다.

	}

}