/* oauth WebMvcConfig.java 2019. 8. 7.
 * Copyright (c) 2001-2019 Alticast Corp. 
 * All rights reserved. http://www.alticast.com/ 
 * This software is the confidential and proprietary information of
 * Alticast Corp. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Alticast.
 */
package com.sookstory.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @FileName WebMvcConfig.java
 * @Project oauth
 * @Author ksko
 * @Date   2019. 8. 7.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST","PUT","DELETE").allowedHeaders("*").allowCredentials(true).maxAge(3600);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("create passwordEncoder()");
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
}
