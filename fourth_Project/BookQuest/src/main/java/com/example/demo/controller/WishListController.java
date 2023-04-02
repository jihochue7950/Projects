package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

@Controller
@RequestMapping(value = "/wishlist")
public class WishListController {

	@Autowired
	WishlistService wishlistService;

	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

	@GetMapping("/")
	public String wishList(Model themodel, Principal principal) {

		return listByPage(1, themodel, principal);
	}

	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model, Principal principal) {

		String userEmail = principal.getName();
		User user = wishlistService.getUserByEmail(userEmail);

		Page<Wishlist> page = wishlistService.findWishListByUserpaging(user, pageNum);
		List<Wishlist> wishList = page.getContent();

		long startCount = (pageNum - 1) * wishlistService.WISHLIST_PER_PAGE + 1;
		long endCount = startCount + wishlistService.WISHLIST_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("currentpage", pageNum);
		model.addAttribute("pre", page.getNumber());
		model.addAttribute("next", (page.getNumber() + 2));
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("wishList", wishList);

		return "wishlist";
	}

	@PostMapping("/save")
	public String savewishlist(Model model, @RequestParam("book") int bookId, Principal principal,
			@ModelAttribute("wishlist") Wishlist wishlist, RedirectAttributes r) {

		String username = principal.getName();
		Optional<User> user = userService.findByID(username);
		User userId = user.get();

		List<Wishlist> wishlistForUser = wishlistService.findByid(userId);

		List<Integer> booksnumber = new ArrayList<>();

		for (int i = 0; i < wishlistForUser.size(); i++) {
			booksnumber.add(wishlistForUser.get(i).getBook().getBookId());
		}

		if (booksnumber.contains(bookId)) {
			Book book = bookService.findById(bookId).get();
			wishlistService.delete(book);

			return "redirect:/book/detail?book=" + bookId;
		} else if (!booksnumber.contains(bookId)) {
			Book book = bookService.findById(bookId).get();
			wishlistService.save(wishlist, book, userId);

		}

		return "redirect:/book/detail?book=" + bookId;
	}

	@GetMapping("/delete/{bookId}")
	public String deletebook(@PathVariable(name = "bookId") int bookId) {
		Book book = bookService.findById(bookId).get();
		wishlistService.delete(book);
		return "redirect:/wishlist/";
	}

}