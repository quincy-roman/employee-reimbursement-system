package com.revature.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.excpetion.ERSException;
import com.revature.model.Reimbursement;
import com.revature.repository.ReimbursementRepository;

@Service
@Transactional
public class ReimbursementService {

	private final ReimbursementRepository repo;

	// Constructor injection.
	@Autowired
	public ReimbursementService(ReimbursementRepository repo) {
		this.repo = repo;
	}

	public boolean insert(Reimbursement reim) throws ERSException {

		// Check for negative amounts.
		if (reim.getAmount() <= 0) {
			throw new ERSException("Service.IMPROPER_AMOUNT");
		}

		reim.setSubmitted(LocalDateTime.now());
		return repo.save(reim) != null ? true : false;
	}

}
