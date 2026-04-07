/*
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
@Table(name="sales_details")
public class SalesDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sales_detail_id")
	private Integer salesDetailId;

	@ManyToOne
	@JoinColumn(name="sales_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Sales sales;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;

	@Column(name="book_status", nullable = false)
	private String bookStatus;
	
	@Column(name="sales_quantity", nullable = false)
	private Integer salesQuantity;
	
	@Column(name="sell_price", nullable = false)
	private Integer sellPrice;
	
	public SalesDetail(){}
	
	@Override
	public String toString() {
		return "SalesDetail [salesDetailId=" + salesDetailId + ", sales=" + sales + ", book=" + book + ", bookStatus="
				+ bookStatus + ", salesQuantity=" + salesQuantity + ", sellPrice=" + sellPrice + "]";
	}

	public Integer getSalesDetailId() {
		return salesDetailId;
	}

	public void setSalesDetailId(Integer salesDetailId) {
		this.salesDetailId = salesDetailId;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(Integer salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	
}
*/
