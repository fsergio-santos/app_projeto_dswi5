package com.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService; 
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//String password = passwordEncoder().encode("123456");
		//System.out.println(password);

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
		
//		auth.inMemoryAuthentication()
//			.withUser("admin")
//			.password(password)
//			.roles("ADMINISTRADOR");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/static/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/login/").permitAll()
				.antMatchers("/roles/**").permitAll()
				.antMatchers("/usuarios/**").permitAll()
				.antMatchers("/pessoa/**").hasAnyRole("ADMINISTRADOR","USUARIO")
				.antMatchers("/pessoa_fisica/**").hasAnyRole("USUARIO","VISITANTE")
				.anyRequest().authenticated();
				
		http.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/home",true)
				.failureUrl("/login")
				.permitAll();
		
		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		
		

		http.csrf().disable();
	
	}
	
	
    @Override	
	public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/resources/**");
    
    }
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
}
