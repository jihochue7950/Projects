package com.example.demo.restcontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

@Controller
public class WishlistRestController {

	@Autowired
	private WishlistService wishListService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@PostMapping("/wish")
	@ResponseBody
	public String save(@RequestBody int bookId, Principal principal) {
		String check = null;
		String username = principal.getName();
		Optional<User> user = userService.findByID(username);
		User userId = user.get();

		List<Wishlist> wishlistForUser = wishListService.findByid(userId);

		List<Integer> booksnumber = new ArrayList<>();

		for (int i = 0; i < wishlistForUser.size(); i++) {
			booksnumber.add(wishlistForUser.get(i).getBook().getBookId());
		}

		if (booksnumber.contains(bookId)) {
			Book book = bookService.findById(bookId).get();
			wishListService.delete(book);
			check = "1";

		} else if (!booksnumber.contains(bookId)) {
			Wishlist wishlist = new Wishlist();
			Book book = bookService.findById(bookId).get();
			wishListService.save(wishlist, book, userId);
			check = "0";
		}

		return check;
	}

}