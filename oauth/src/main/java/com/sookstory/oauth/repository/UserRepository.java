/* oauth UserRepository.java 2019. 8. 7.
 * Copyright (c) 2001-2019 Alticast Corp. 
 * All rights reserved. http://www.alticast.com/ 
 * This software is the confidential and proprietary information of
 * Alticast Corp. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Alticast.
 */
package com.sookstory.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sookstory.oauth.entity.User;

/**
 * @FileName UserRepository.java
 * @Project oauth
 * @Author ksko
 * @Date   2019. 8. 7.
 */
public interface UserRepository extends JpaRepository<User,String>{

}
