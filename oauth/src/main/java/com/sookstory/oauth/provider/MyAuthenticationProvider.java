/* oauth MyAuthenticationProvider.java 2019. 8. 7.
 * Copyright (c) 2001-2019 Alticast Corp. 
 * All rights reserved. http://www.alticast.com/ 
 * This software is the confidential and proprietary information of
 * Alticast Corp. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Alticast.
 */
package com.sookstory.oauth.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sookstory.oauth.entity.User;
import com.sookstory.oauth.repository.UserRepository;

/**
 * @FileName MyAuthenticationProvider.java
 * @Project oauth
 * @Author ksko
 * @Date   2019. 8. 7.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//인증할 때 이름이랑 비번 받아서 User에서 userDetail 가져와서 반환 
		
		String name = authentication.getName();
		System.out.println("name : " + name);
		String password = authentication.getCredentials().toString();
		User user = userRepository.findById(name).get();
		System.out.println("password : " + password + " , real : " + user.getPassword());
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			System.out.println("두 비밀번호는 다르다!!");
			throw new BadCredentialsException("password is not valid");
		}
		
		return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
		//return new UsernamePasswordAuthenticationToken(name, password, null);
	}

	//AuthenticationProvider 에서 반환한 Authentication이 유효한지를 반환 
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
