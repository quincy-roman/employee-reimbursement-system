package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.User;
import com.revature.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	/**
	 * Basic login, takes the full User object
	 * as parameter. Returns 200 or 401 depending on
	 * whether or not the user exists.
	 * 
	 * @param
	 * @return ok, badRequest
	 * @author qroma
	 */
	@PostMapping(path = "/login")
	public ResponseEntity<Integer> login(@RequestBody User user){
		
		Integer id = userService.login(user.getEmail(), user.getPassword());
		
		if(id == null) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(id);			
		
	}

}
