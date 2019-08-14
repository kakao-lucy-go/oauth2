/* oauth SecurityConfig.java 2019. 8. 7.
 * Copyright (c) 2001-2019 Alticast Corp. 
 * All rights reserved. http://www.alticast.com/ 
 * This software is the confidential and proprietary information of
 * Alticast Corp. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Alticast.
 */
package com.sookstory.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.sookstory.oauth.provider.MyAuthenticationProvider;

/**
 * 
 * WebSecurityConfigurer 인스턴스 생성.
 * WebSecurityConfigurerAdapter + EnableWebSecurity 어노테이션 콤보는 필수적.
 * Security config 구성
 * 앞 단 필터링 
 * 
 * @FileName SecurityConfig.java
 * @Project oauth
 * @Author ksko
 * @Date   2019. 8. 7.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private MyAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//인메모리 
		System.out.println("custom authenticatoin provider 생성 ");
		auth.authenticationProvider(authenticationProvider);
	}
	
	
	//지정한 url들은 모든 권한 접근 승인 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		.headers().frameOptions().disable()
		.and()
		.authorizeRequests().antMatchers("/oauth/**","/oauth2/callback", "/oauth/token").permitAll()
		.and()
		.formLogin().and()
		.httpBasic();
		
		/* http.authorizeRequests()
	        .antMatchers("/**").hasRole("USER").and().formLogin().and().httpBasic();*/
	}
}
