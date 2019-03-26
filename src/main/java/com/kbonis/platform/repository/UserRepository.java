package com.kbonis.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbonis.platform.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByUserNameAndPassword(String userName,String password);
}
