package com.revature.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;

@RestController
public class ReimbursementController {
	
	@Autowired
	ReimbursementService reimService;
	
	/**
	 * Insert a new Reimbursement request for an
	 * employee. Will return a 201 with the URI
	 * or a 401 if successful.
	 * 
	 * @param reim
	 * @return
	 */
	public ResponseEntity<Integer> insert(Reimbursement reim){
		
		boolean flag = reimService.insert(reim);
		
		if(flag) {
			return ResponseEntity.created(URI.create("")).build();
		}
		
		return ResponseEntity.badRequest().build();
		
	}

}
