package com.surfersolution.workshopionic.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.surfersolution.workshopionic.domain.enums.PaymentState;

@Entity
public class SlipPayment extends Payment {
	
	private static final long serialVersionUID = 1L;

	private Date dueDate;
	private Date paymentDate;
	
	public SlipPayment() {
		
	}

	public SlipPayment(Integer id, PaymentState paymentState, Order order, Date dueDate, Date paymentDate) {
		super(id, paymentState, order);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	
	
}
