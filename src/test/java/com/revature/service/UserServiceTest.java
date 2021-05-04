package com.revature.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.revature.excpetion.ERSException;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Mock
	private UserRepository repo;

	@InjectMocks
	private UserService service;

	private User user;
	private Role role;

	@BeforeEach
	void setUp() {
		role = new Role(1, "Employee");
		user = new User(null, "email", "password", "firstName", "lastName", role);
	}

	@Test
	void shouldSaveUserSuccessfully() throws Exception {
		given(repo.findByEmail(user.getEmail())).willReturn(Optional.empty());
		given(repo.save(user)).willAnswer(invocation -> invocation.getArgument(0));
		
		User saved = service.register(user);
		
		assertThat(saved).isNotNull();
		
		verify(repo).save(any(User.class));
	}
	
	@Test
	void shouldThrowErrorIfUserAlreadyExists() {
		user.setUserId(1L);
		given(repo.findByEmail(user.getEmail())).willReturn(Optional.of(user));
		
		assertThrows(ERSException.class, () -> {
			service.register(user);
		});
		
		verify(repo, never()).save(any(User.class));
	}
	
	@Test
	void shouldLoginWithGoodCreds() throws Exception {
		user.setUserId(1L);
		given(repo.findByEmailAndPassword(user.getEmail(), user.getPassword())).willReturn(Optional.of(user));
		
		User success = service.login(user.getEmail(), user.getPassword());
		
		assertEquals(success, user);
		
		// Make sure it only happened once.
		verify(repo).findByEmailAndPassword(any(String.class), any(String.class));
		
	}
	
	@Test
	void shouldThrowErrorWithBadCreds() {
		given(repo.findByEmailAndPassword(user.getEmail(), user.getPassword())).willReturn(Optional.empty());
		
		assertThrows(ERSException.class, () -> {
			service.login(user.getEmail(), user.getPassword());
		});
		
		verify(repo).findByEmailAndPassword(any(String.class), any(String.class));
	}

}
