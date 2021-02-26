package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer>{
	
	public List<Reimbursement> findAllByStatus(ReimbursementStatus status);
	
	public List<Reimbursement> findAllByType(ReimbursementType type);
	
	public List<Reimbursement> findAllByAuthor(int authorId);

}
