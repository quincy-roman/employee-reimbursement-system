package com.revature.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.excpetion.ERSException;
import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;

@RestController
@RequestMapping("/api/reimbursement")
public class ReimbursementController {
	
	private final ReimbursementService service;
	
	@Autowired
	public ReimbursementController(ReimbursementService service) {
		this.service = service;
	}
	
	/**
	 * Insert a new Reimbursement request for an
	 * employee. Will return a 201 with the URI
	 * or a 400 if unsuccessful.
	 * 
	 * @param reim
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Long> insert(Reimbursement reim){	// TODO Consider making this accept a list instead.
		
		try {
			if(service.insert(reim)) {
				return ResponseEntity.created(URI.create("")).build();
			}
			
			// Otherwise, the insert failed, send a 401.
			return ResponseEntity.badRequest().build();
			
		} catch (ERSException e) {
			// If something happens, send 401.
			return ResponseEntity.badRequest().build();
		}		
	}

}
