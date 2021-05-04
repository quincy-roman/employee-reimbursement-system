package com.revature.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.excpetion.ERSException;
import com.revature.model.User;
import com.revature.repository.UserRepository;

@Service
@Transactional
public class UserService {

	private final UserRepository repo;

	@Autowired
	public UserService(UserRepository repo) {
		this.repo = repo;
	}

	/**
	 * Check to see if the requested user exists in the database. If not, return
	 * null.
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws ERSException
	 */
	public User login(String email, String password) throws ERSException {
		Optional<User> u = repo.findByEmailAndPassword(email, password);
		if(u.isEmpty()) {
			throw new ERSException("Service.USER_NOT_FOUND");
		}
		return u.get();
	}

	public User register(User u) throws ERSException {
		Optional<User> user = repo.findByEmail(u.getEmail());
		
		if(user.isPresent()) {
			throw new ERSException("Service.USER_EXISTS");
		}
		
		return Optional.of(repo.save(u)).get();
	}
	
	public void updateUser(User u) {

	}
	

}
