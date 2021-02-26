package com.revature.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.User;
import com.revature.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	ObjectMapper mapper = new ObjectMapper();
	
	@MockBean
	private UserService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserController controller;
	
	private List<User> users;
	
	/*
	 * Ensure the application context is loaded.
	 * If not, all tests should and will fail.
	 */
	@Test
	public void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}
	
	@BeforeEach
	public void init() {
		users = new ArrayList<>();
		users.add(new User(1, "test1@email.com", "pass1", "Tester", "One", null));
		users.add(new User(2, "test2@email.com", "pass2", "Teste", "Two", null));
		users.add(new User(3, "test3@email.com", "pass3", "Test", "Three", null));
	}
	
	/**
	 * Ensure that the login functionality works
	 * when given proper credentials.
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifyLogin() throws Exception{
		
		int pos = 0;
		
		// Show that logging in with the established trainers works properly.
		for(User user : users) {
			when(service.login(user.getEmail(), user.getPassword())).thenReturn(users.get(pos).getUserId());
			
			this.mockMvc.perform(post("/login")
								.contentType("application/json")
								.content(mapper.writeValueAsString(user)))
						.andDo(print())
						.andExpect(status().isOk())
						.andExpect(content().contentType("application/json"))
						.andExpect(content().string(mapper.writeValueAsString(user.getUserId())));
			
			pos++;
		}		
	}

}
