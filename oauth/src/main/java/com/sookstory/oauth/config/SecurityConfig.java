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
 * WebSecurityConfigurer �씤�뒪�꽩�뒪 �깮�꽦.
 * WebSecurityConfigurerAdapter + EnableWebSecurity �뼱�끂�뀒�씠�뀡 肄ㅻ낫�뒗 �븘�닔�쟻.
 * Security config 援ъ꽦
 * �븵 �떒 �븘�꽣留� 
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
		//�씤硫붾え由� 
		System.out.println("custom authenticatoin provider �깮�꽦 ");
		auth.authenticationProvider(authenticationProvider);
	}
	
	
	//吏��젙�븳 url�뱾�� 紐⑤뱺 沅뚰븳 �젒洹� �듅�씤 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		//xml configuration이 아니라 java configuration을 사용하는 옵션
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
