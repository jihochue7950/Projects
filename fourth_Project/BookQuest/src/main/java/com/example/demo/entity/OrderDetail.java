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
@Table(name="order_details")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_detail_id")
	private Integer orderDetailId;

	@ManyToOne
	@JoinColumn(name="order_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;

	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@Column(name="book_status")
	private String bookStatus;
	
	@ManyToOne
	@JoinColumn(name="books_branch_id")
	private BooksBranch BooksBranch;
	
	@Column(name="order_quantity", nullable = false)
	private Integer orderQuantity;
	
	@Column(nullable = false)
	private Integer price;
	
	public OrderDetail(){}

	@Override
	public String toString() {
		return "OrderDetail [orderDetailId=" + orderDetailId + ", order=" + order + ", book=" + book + ", bookStatus="
				+ bookStatus + ", BooksBranch=" + BooksBranch + ", orderQuantity=" + orderQuantity + ", price=" + price
				+ "]";
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public BooksBranch getBooksBranch() {
		return BooksBranch;
	}

	public void setBooksBranch(BooksBranch booksBranch) {
		BooksBranch = booksBranch;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	
	

}
