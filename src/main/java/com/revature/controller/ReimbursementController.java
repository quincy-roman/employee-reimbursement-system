package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import com.revature.service.ReimbursementService;

@RestController
public class ReimbursementController {
	
	@Autowired
	ReimbursementService reimService;

}
