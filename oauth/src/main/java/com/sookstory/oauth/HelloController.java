/* oauth HelloController.java 2019. 8. 7.
 * Copyright (c) 2001-2019 Alticast Corp. 
 * All rights reserved. http://www.alticast.com/ 
 * This software is the confidential and proprietary information of
 * Alticast Corp. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Alticast.
 */
package com.sookstory.oauth;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @FileName HelloController.java
 * @Project oauth
 * @Author ksko
 * @Date 2019. 8. 7.
 */
@RestController
public class HelloController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/oauth2/callback")
	public String callbackSocial(@RequestParam String code) {
		System.out.println("code : " + code);
		String credentials = "client2:password";
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + encodedCredentials);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", code);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", "http://localhost:8081/oauth2/callback");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request,
				String.class);
		
		return response.getBody();
	}
	
	@GetMapping(value="/test")
	public String getRoot() {
		return "OK!!";
	}
	
	@GetMapping(value = "/oauth2/token/refresh")
	public String refreshToken(@RequestParam String refreshToken) {
	 
	    String credentials = "client2:password";
	    String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
	 
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
	    headers.add("Authorization", "Basic " + encodedCredentials);
	 
	    System.out.println("authorization : " + "Basic " + encodedCredentials);
	    System.out.println("refresh token : " + refreshToken);
	    
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("refresh_token", refreshToken);
	    params.add("grant_type", "refresh_token");
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
	    System.out.println("request : " + request.toString());
	    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String.class);
	    
	    return response.getBody();
	    
	}
}
