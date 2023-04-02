package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Review;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {

//   @Query("SELECT r FROM Review r WHERE r.book=:book")
//   public List<Object> findByBookid(@Param("book") Book book);

	// 특정 책에 대한 리뷰를 최신순으로 정렬.
	@Query("SELECT r FROM Review r WHERE r.book =:book ORDER BY r.commentDate DESC")
	public List<Review> findByBookid(@Param("book") Book book);

	// 특정 책의 리뷰 개수
	@Query("SELECT COUNT(r.book) FROM Review r WHERE r.book=:book")
	public Float countbook(@Param("book") Book book);

	// 별점의 총합
	@Query("SELECT SUM(r.starRating) FROM Review r WHERE r.book=:book")
	public Float sumstar(@Param("book") Book book);

	// 특정 책의 별점과 그 별점에 매겨진 개수를 가져옴.
	@Query("SELECT COUNT(r.starRating), starRating FROM Review r WHERE r.book.bookId=:book GROUP BY r.starRating")
	public List<Object[]> countStarRating(@Param("book") Integer bookId);

	// 특정 책의 별점과 그 별점에 매겨진 개수를 DESC로 정렬해서 가져옴.
	@Query("SELECT COUNT(r.starRating), starRating FROM Review r WHERE r.book.bookId=:book GROUP BY r.starRating ORDER BY r.starRating DESC")
	public List<Object[]> countStarRatingDesc(@Param("book") Integer bookId);
}