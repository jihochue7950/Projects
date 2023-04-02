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
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Integer cartId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@Column(name = "book_quantity", nullable = false)
	private Integer bookQuantity;

	public Cart() {
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user=" + user + ", book=" + book + ", bookQuantity=" + bookQuantity + "]";
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
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

	public Integer getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(Integer cartQuantity) {
		this.bookQuantity = cartQuantity;
	}

}
