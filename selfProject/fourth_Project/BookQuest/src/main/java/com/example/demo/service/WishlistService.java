package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.repository.WishlistRepository;

@Service
@Transactional
public class WishlistService {

	@Autowired
	private WishlistRepository repo;

	@Autowired
	public static final int WISHLIST_PER_PAGE = 40;

	public User getUserByEmail(String userEmail) {
		return repo.getUserByEmail(userEmail);
	}

	public Page<Wishlist> findWishListByUserpaging(User user, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, WISHLIST_PER_PAGE);
		return repo.findAll(pageable, user);
	}

	public void save(Wishlist wishlist, Book book, User userId) {
		Wishlist wishlists = new Wishlist();
		wishlists.setWishlistQuantity(1);
		wishlists.setBook(book);
		wishlists.setUser(userId);
		repo.save(wishlists);

	}

	public List<Wishlist> findByid(User userId) {

		return repo.findByID(userId);
	}

	public void delete(Book book) {
		repo.deleteById(book);

	}

}