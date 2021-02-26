package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.User;
import com.revature.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	public Integer login(String email, String password) {
		User u = userRepo.findByEmailAndPassword(email, password);
		return u.getUserId();
	}

}
