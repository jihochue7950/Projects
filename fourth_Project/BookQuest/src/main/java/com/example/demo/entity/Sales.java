/*
package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="sales")
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sales_id")
	private Integer salesId;
	
    @OneToMany(mappedBy = "sales", fetch = FetchType.EAGER)
    private List<SalesDetail> salesDetails;

	@ManyToOne
	@JoinColumn(name="user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="branches_id")
	private Branches branches;
	
	@Column(name="sales_date")
	private LocalDateTime salesDate;
	
	@Column(name="total_price")
	private Integer totalPrice;
	
	@Column(name="address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name= "payment_id")
	private Payment payment;

	@Column(name= "delivery_status", length = 45, nullable = false)
	private String deliveryStatus;
	
	public Sales(){}

	@Override
	public String toString() {
		return "Sales [salesId=" + salesId + ", user=" + user + ", branches=" + branches + ", salesDate=" + salesDate
				+ ", totalPrice=" + totalPrice + ", address=" + address + ", payment=" + payment + ", deliveryStatus="
				+ deliveryStatus + "]";
	}

	public Integer getSalesId() {
		return salesId;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Branches getBranches() {
		return branches;
	}

	public void setBranches(Branches branches) {
		this.branches = branches;
	}

	public LocalDateTime getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(LocalDateTime salesDate) {
		this.salesDate = salesDate;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public List<SalesDetail> getSalesDetails() {
		return salesDetails;
	}

	public void setSalesDetails(List<SalesDetail> salesDetails) {
		this.salesDetails = salesDetails;
	}
	
}
*/
