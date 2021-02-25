package com.revature.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int reimbursmentId;
	
	@Column(name="reimbursement_amount")
	private double amount;
	
	@Column(name="submitted")
	private Timestamp submitted;
	
	@Column(name="resolved")
	private Timestamp resolved;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="User_FK_Author", referencedColumnName="user_id")
	private User author;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="User_FK_Resolver", referencedColumnName="user_id")
	private User resolver;	//I believe these are who makes and approves the request.

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Status_FK", referencedColumnName="status_id")
	private ReimbursementStatus status;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Type_FK", referencedColumnName="type_id")
	private ReimbursementType type;

}
