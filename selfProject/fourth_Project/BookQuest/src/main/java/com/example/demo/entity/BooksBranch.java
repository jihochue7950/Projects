package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books_branch")
public class BooksBranch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "books_branch_id")
	private Integer booksBranchId;

	@ManyToOne
	@JoinColumn(name = "branches_id")
	private Branches branches;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@Column
	private String status; // condition;

	@Column(nullable = false)
	private Integer quantity;

	public BooksBranch() {}

	public BooksBranch(Branches branches, Book book, String status, Integer quantity) {
		this.branches = branches;
		this.book = book;
		this.status = status;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "BooksBranch [booksBranchId=" + booksBranchId + ", branches=" + branches + ", book=" + book + ", status="
				+ status + ", quantity=" + quantity + "]";
	}

	public Integer getBooksBranchId() {
		return booksBranchId;
	}

	public void setBooksBranchId(Integer booksBranchId) {
		this.booksBranchId = booksBranchId;
	}

	public Branches getBranches() {
		return branches;
	}

	public void setBranches(Branches branches) {
		this.branches = branches;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
