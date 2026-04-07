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
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository repo;

	@Autowired
	public static final int CARTS_PER_PAGE = 4;

	public User getUserByEmail(String userEmail) {
		return repo.getUserByEmail(userEmail);
	}

	public List<Cart> findCartByUser(User user) {
		return repo.findCartByUser(user);
	}

	public Cart findById(int cartId) {
		return repo.findById(cartId).get();
	}

	public void save(Cart cart, int number, Book bookId, User userId) {
		Cart carts = new Cart();
		carts.setBook(bookId);
		carts.setUser(userId);
		carts.setBookQuantity(number);
		repo.save(carts);
	}

	public void deletecartId(int cartId) {
		repo.deleteById(cartId);
	}

	public void deleteCartByUser(User user) {
		repo.deleteByUser(user);
	}

	public void upQuantity(int cartId, int UpcartQuantity) {
		repo.upQuantity(cartId, UpcartQuantity);
	}

	public void DownQuantity(int cartId, int DowncartQuantity) {
		repo.downQuantity(cartId, DowncartQuantity);
	}

	public Page<Cart> findCartByUserpaging(User user, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, CARTS_PER_PAGE);
		return repo.findAll(pageable, user);
	}

	public Cart findCartByUserAndBook(User user, Book book) {
		return repo.findCartByUserAndBookId(user, book);
	}

	public void deleteCartByCartId(int carts) {
		repo.deleteCartByCartId(carts);
	}
}