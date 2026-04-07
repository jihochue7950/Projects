package com.example.demo.entity;

import java.time.LocalDateTime;

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
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reviews_id")
	private Integer reviewsid;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@Column(name = "star_rating")
	private Integer starRating;

	@Column(nullable = false)
	private String comment;

	@Column(name = "comment_date", length = 255)
	private LocalDateTime commentDate;

	public Review() {
	}
	
	public Review(Integer reviewsid) {
		this.reviewsid = reviewsid;
	}

	@Override
	public String toString() {
		return "Review [reviewsid=" + reviewsid + ", user=" + user + ", book=" + book + ", starRating=" + starRating
				+ ", comment=" + comment + ", commentDate=" + commentDate + "]";
	}

	public Integer getReviewsid() {
		return reviewsid;
	}

	public void setReviewsid(Integer reviewsid) {
		this.reviewsid = reviewsid;
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

	public Integer getStarRating() {
		return starRating;
	}

	public void setStarRating(Integer starRating) {
		this.starRating = starRating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(LocalDateTime commentDate) {
		this.commentDate = commentDate;
	}

}