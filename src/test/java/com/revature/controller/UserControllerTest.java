package com.revature.controller;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.excpetion.ERSException;
import com.revature.model.User;
import com.revature.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {


	@MockBean
	private UserService service;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	private List<User> users;
	
	private User newUser = new User(null, "email", "password", "first", "last", null);

	@BeforeEach
	public void init() {
		users = new ArrayList<>();
		users.add(new User(1L, "test1@email.com", "pass1", "Tester", "One", null));
		users.add(new User(2L, "test2@email.com", "pass2", "Teste", "Two", null));
		users.add(new User(3L, "test3@email.com", "pass3", "Test", "Three", null));
	}

	/**
	 * Ensure that the login functionality works when given proper credentials.
	 * 
	 * @throws Exception
	 */
	@Test
	void verifyLogin() throws Exception {

		int pos = 0;

		// Show that logging in with the established trainers works properly.
		for (User user : users) {
			when(service.login(user.getEmail(), user.getPassword())).thenReturn(users.get(pos));

			this.mockMvc
					.perform(post("/api/login")
							.contentType("application/json")
							.content(mapper.writeValueAsString(user)))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andExpect(content().string(mapper.writeValueAsString(user)));

			pos++;
		}
	}
	
	@Test
	void shouldReturn401ForIncorrectUserCreds() throws Exception {
		User badUser = new User(1L, "wrongemail", "wrongpass", "", "", null);
		given(service.login("wrongemail", "wrongpass")).willThrow(ERSException.class);
		
		this.mockMvc.perform(post("/api/login")
							.contentType("application/json")
							.content(mapper.writeValueAsString(badUser)))
						.andExpect(status().isUnauthorized());
	}
	
	/**
	 * Test to see that a user can be created
	 * and return a 201 status.
	 */
	@Test
	void shouldCreateNewUser() throws Exception {
		given(service.register(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));
		
		this.mockMvc.perform(post("/api/users")
							.contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(newUser)))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.email", is(newUser.getEmail())))
					.andExpect(jsonPath("$.password", is(newUser.getPassword())))
					.andExpect(jsonPath("$.firstName", is(newUser.getFirstName())))
					.andExpect(jsonPath("$.lastName", is(newUser.getLastName())));
		
	}
	
	/**
	 * If the user already exists, a 400
	 * status code should be returned.
	 * 
	 * @throws Exception
	 */
	@Test
	void shouldReturn400WhenUserExists() throws Exception {
		given(service.register(any(User.class))).willThrow(ERSException.class);
		
		this.mockMvc.perform(post("/api/users")
							.contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(newUser)))
					.andDo(print())
					.andExpect(status().isBadRequest())
					.andExpect(header().string("Content-Type", is("application/problem+json")));
	}

}
