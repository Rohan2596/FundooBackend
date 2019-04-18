package com.bridgelabz.fundoo.user.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.user.model.User;

public interface UserRespository extends JpaRepository<User,Integer>{
	public Optional<User> findByEmailId(String emailId);
	
}
