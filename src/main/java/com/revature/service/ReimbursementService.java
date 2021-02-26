package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.Reimbursement;
import com.revature.repository.ReimbursementRepository;

@Service
public class ReimbursementService {
	
	@Autowired
	ReimbursementRepository reimbursementRepo;
	
	public boolean insert(Reimbursement reim) {
		return reimbursementRepo.save(reim) != null ? true : false;
	}

}
