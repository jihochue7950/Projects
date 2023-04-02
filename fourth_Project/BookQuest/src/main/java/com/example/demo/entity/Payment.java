package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private Integer paymentId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(length = 32, nullable = false)
	private String bank;
	
	@Column(name = "account_number", length = 32, nullable = false)
	private String accountNumber;
	
	@Column(name = "externalPayment")
	private String externalPayment;
	
	
	public Payment(){}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", user=" + user + ", bank=" + bank + ", accountNumber="
				+ accountNumber + ", externalPayment=" + externalPayment + "]";
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getExternalPayment() {
		return externalPayment;
	}

	public void setExternalPayment(String externalPayment) {
		this.externalPayment = externalPayment;
	}

}
