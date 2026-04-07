package com.example.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Integer bookId;

	@Column(length = 128, nullable = false)
	private String title;

	@Column(length = 32, nullable = false)
	private String author;

	@Column(length = 32, nullable = false)
	private String publisher;

	@Column(name = "publication_date", nullable = false)
	private LocalDate publicationDate;

	@Column(nullable = false)
	private Integer price;

	@Column(name = "discount_rate")
	private Integer discountRate = 0;

	@Column(length = 128, nullable = false)
	private String image;

	@Column(name = "avg_star")
	private Float avgStar = (float) 0;

	@Column(nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Transient
	public String getImagePath() {
		if (bookId == null || image == null)
			return "/images/defaultBookCover.png";
		return "/bookCover/" + this.category.getName() + "/" + this.image;
	}

	public Book() {
	}

	public Book(Integer bookId) {
		this.bookId = bookId;
	}
	
	public Book(String author, String description, int discountRate,  String image, int price,
			String publicationDate, String publisher, String title, int category, int avgStar) {
		this.author = author;
		this.description = description;
		this.discountRate = discountRate;
		this.image = image;
		this.price = price;
		this.publicationDate = LocalDate.parse(publicationDate);
		this.publisher = publisher;	
		this.title = title;
		this.category = new Category(category);
		this.avgStar = (float) avgStar;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", publicationDate=" + publicationDate + ", price=" + price + ", discountRate=" + discountRate
				+ ", image=" + image + ", avgStar=" + avgStar + ", description=" + description + ", category="
				+ category + "]";
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Float getAvgStar() {
		return avgStar;
	}

	public void setAvgStar(Float avgStar) {
		this.avgStar = avgStar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
