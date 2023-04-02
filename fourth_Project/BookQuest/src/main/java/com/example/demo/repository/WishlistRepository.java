package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;

@Repository
public interface WishlistRepository extends PagingAndSortingRepository<Wishlist, Integer> {

	@Query("SELECT u FROM User u WHERE u.email =:email")
	public User getUserByEmail(@Param("email") String email);

	@Query("SELECT w FROM Wishlist w WHERE w.user =:user")
	public Page<Wishlist> findAll(Pageable pageable, User user);

	@Query("SELECT w FROM Wishlist w WHERE w.user =:userId")
	public List<Wishlist> findByID(User userId);

	@Modifying
	@Query("DELETE FROM Wishlist w WHERE w.book =:book")
	public void deleteById(Book book);

}