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
		Optional<User> user = repo.findByEmailAndPassword(email, password);
		if(user.isEmpty()) {
			throw new ERSException("Service.FAILED_LOGIN");
		}
		return user.get();
	}

	public User register(User user) throws ERSException {
		Optional<User> opUser = repo.findByEmail(user.getEmail());
		
		if(opUser.isPresent()) {
			throw new ERSException("Service.USER_EXISTS");
		}
		
		return repo.save(user);
	}
	
	public User updateUser(Long id) throws ERSException {
		User user = findById(id);
		return repo.save(user);
	}
	
	public User deleteUser(Long id) throws ERSException {
		// Get the user's id, if they exist in the database.
		User user = findById(id);
		
		repo.delete(user);
		
		return user;
	}

	User findById(Long userId) throws ERSException {
		Optional<User> user = repo.findById(userId);
		
		if(user.isEmpty()) {
			throw new ERSException("Service.USER_NOT_FOUND");
		}
		
		return user.get();
	}
	

}
