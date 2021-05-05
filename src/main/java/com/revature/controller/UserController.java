package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.excpetion.ERSException;
import com.revature.model.User;
import com.revature.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private final UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
	
	/**
	 * Basic login, takes the full User object
	 * as parameter. Returns 200 or 401 depending on
	 * whether or not the user exists.
	 * 
	 * @param
	 * @return OK, badRequest
	 * @author qroman
	 */
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		
		try {
			user = service.login(user.getEmail(), user.getPassword());
			return ResponseEntity.ok(user);			
		} catch (ERSException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();	
			// TODO Might want to return the username used.
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> register(@RequestBody User user){
		try {
			user = service.register(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}catch(ERSException e) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).build();
		}
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> update(@PathVariable Long id){
		try {
			User user = service.updateUser(id);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (ERSException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id){
		try {
			User user = service.deleteUser(id);
			return ResponseEntity.ok(user);
		} catch (ERSException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
