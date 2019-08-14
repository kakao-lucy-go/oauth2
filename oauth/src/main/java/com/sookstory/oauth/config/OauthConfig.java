/* oauth OauthConfig.java 2019. 8. 7.
 * Copyright (c) 2001-2019 Alticast Corp. 
 * All rights reserved. http://www.alticast.com/ 
 * This software is the confidential and proprietary information of
 * Alticast Corp. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Alticast.
 */
package com.sookstory.oauth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.sookstory.oauth.MyUserDetailsService;

/**
 * 
 * 오쓰서버. 코드 및 토큰 발급 
 * 
 * @FileName OauthConfig.java
 * @Project oauth
 * @Author ksko
 * @Date   2019. 8. 7.
 */
@Configuration
@EnableAuthorizationServer
@EnableJpaRepositories(basePackages = "com.sookstory.oauth.repository")
@EntityScan("com.sookstory.oauth.entity")
public class OauthConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${security.oauth2.jwt.signkey}")
	private String signKey;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	//db 쓰겠다.
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		//인메모리
		System.out.println("passwordEncoder bean override");
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()")
		.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
/*		//토큰을 db에 관리한다.
		endpoints.tokenStore(new JdbcTokenStore(dataSource));*/
		
		//토큰 -> jwt 토큰으로 변경한다. 
		//기존 토큰은 디비에서 관리하지만, jwt토큰은 관리할 필요가 없다.
		super.configure(endpoints);
		
		endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailsService);
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(signKey);
		return jwtAccessTokenConverter;
	}
}
