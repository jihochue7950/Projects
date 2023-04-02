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
@Table(name = "wishlist")
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wishlist_id")
	private Integer wishlistId;

	@ManyToOne
	@JoinColumn(name="user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@Column(name = "wishlist_quantity", nullable = false)
	private Integer wishlistQuantity;

	public Wishlist(){}

	@Override
	public String toString() {
		return "Wishlist [wishlistId=" + wishlistId + ", user=" + user + ", book=" + book + ", wishlistQuantity="
				+ wishlistQuantity + "]";
	}

	public Integer getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Integer wishlistId) {
		this.wishlistId = wishlistId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getWishlistQuantity() {
		return wishlistQuantity;
	}

	public void setWishlistQuantity(Integer wishlistQuantity) {
		this.wishlistQuantity = wishlistQuantity;
	}
	
	

}