package com.core.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.core.Rest.CustomAccessDeniedHandler;
import com.core.Rest.JwtAuthenticationTokenFilter;
import com.core.Rest.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /rest/**
		http.csrf().ignoringAntMatchers("/**");
		  http.cors().and();
		  http.authorizeRequests().antMatchers("/home**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/customer_result").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/send_appointment").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.GET, "/check_available_case").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/cancel_appointment").permitAll().antMatchers(HttpMethod.GET, "/**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll();
		  http.antMatcher("/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
		    .antMatchers(HttpMethod.POST, "/admin/check_all_appointment").access("hasRole('ROLE_ADMIN')")
		    .antMatchers(HttpMethod.POST, "/admin/form_filter").permitAll()
		    .antMatchers(HttpMethod.POST, "/admin/config_schedule").permitAll()
		    .antMatchers(HttpMethod.POST, "/admin/check_schedule").permitAll()
		    .antMatchers(HttpMethod.POST, "/admin/update_appointment").permitAll()
		    .antMatchers(HttpMethod.POST, "/admin/check_one_appointment").permitAll()
		    .antMatchers(HttpMethod.POST, "/admin/get_customer_history").permitAll().and()
		    .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
		    .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
		    
	}
}