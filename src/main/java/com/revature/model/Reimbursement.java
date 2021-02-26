package com.revature.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data@AllArgsConstructor
public class Reimbursement {
	
	@Id
	@Column(name="reimbursement_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reimbursmentId;
	
	@Column(name="reimbursement_amount")
	private double amount;
	
	private Timestamp submitted;
	
	private Timestamp resolved;
	
	private String description;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User author;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User resolver;

	private ReimbursementStatus status;
	
	private ReimbursementType type;

}
