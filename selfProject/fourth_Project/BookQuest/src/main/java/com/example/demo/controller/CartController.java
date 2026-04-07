package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;
	@Autowired
	CartService cartService;

	@GetMapping("/")
	public String CartList(Model themodel, Principal principal) {
		int totalPrice = 0;
		String userEmail = principal.getName();
		User user = cartService.getUserByEmail(userEmail);

		List<Cart> cartList = cartService.findCartByUser(user);

		themodel.addAttribute("cartList", cartList);
		List<Cart> cartLists = cartService.findCartByUser(user);

		for (int i = 0; i < cartLists.size(); i++) {
			if (cartLists.get(i).getBook().getDiscountRate() == 0) {
				int Price = cartLists.get(i).getBook().getPrice() * cartLists.get(i).getBookQuantity();

				totalPrice += Price;
			} else if (cartLists.get(i).getBook().getDiscountRate() != 0) {
				int Price = (int) (cartLists.get(i).getBook().getPrice()
						* (1 - cartLists.get(i).getBook().getDiscountRate() * 0.01))
						* cartLists.get(i).getBookQuantity();
				totalPrice += Price;
			}

		}
		themodel.addAttribute("totalPrice", totalPrice);
		return "cart";
	}

//	@GetMapping("/page/{pageNum}")
//	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model, Principal principal) {
//		int totalPrice = 0;
//		String userEmail = principal.getName();
//		User user = cartService.getUserByEmail(userEmail);
//
//		Page<Cart> page = cartService.findCartByUserpaging(user, pageNum);
//		List<Cart> cartList = page.getContent();
//
//		long startCount = (pageNum - 1) * cartService.CARTS_PER_PAGE + 1;
//		long endCount = startCount + cartService.CARTS_PER_PAGE - 1;
//		if (endCount > page.getTotalElements()) {
//			endCount = page.getTotalElements();
//		}
//
//		List<Cart> cartLists = cartService.findCartByUser(user);
//
//		for (int i = 0; i < cartLists.size(); i++) {
//			int Price = cartLists.get(i).getBook().getPrice() * cartLists.get(i).getBookQuantity();
//
//			totalPrice += Price;
//		}
//		model.addAttribute("totalPrice", totalPrice);
//		model.addAttribute("currentpage", pageNum);
//		model.addAttribute("pre", page.getNumber());
//		model.addAttribute("next", (page.getNumber() + 2));
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("startCount", startCount);
//		model.addAttribute("endCount", endCount);
//		model.addAttribute("totalItems", page.getTotalElements());
//		model.addAttribute("cartList", cartList);
//
//		return "cart";
//	}

	@PostMapping("/save")
	public String savecart(Model model, @RequestParam("number") int number, Principal principal,
			@Param("book") int book, @ModelAttribute("cart") Cart cart, RedirectAttributes r,
			@RequestParam(required = false, name = "theme") String theme) {
		String userEmail = principal.getName();
		User users = userService.getUserByEmail(userEmail);
		List<Cart> cartBeforeSave = cartService.findCartByUser(users);
		List<Book> bookBeforeSave = new ArrayList<Book>();
		Book findBook = bookService.findById(book).get();
		for (int i = 0; i < cartBeforeSave.size(); i++) {
			bookBeforeSave.add(cartBeforeSave.get(i).getBook());
		}
		if (bookBeforeSave.contains(findBook)) {
			if (theme != null) {
				r.addFlashAttribute("cartMessage", "이미 장바구니에 담겨져 있습니다");
				return "redirect:/book/" + theme;
			}
			r.addFlashAttribute("cartMessage", "이미 장바구니에 담겨져 있습니다");
			return "redirect:/book/detail?book=" + book;
		}

		if (number == 0) {
			r.addFlashAttribute("cartMessage", "책의 수량을 선택해주세요");
		} else if (number != 0) {
			if (theme != null) {
				Optional<Book> books = bookService.findById(book);
				String username = principal.getName();
				Optional<User> user = userService.findByID(username);
				User userId = user.get();
				cartService.save(cart, number, books.get(), userId);
				return "redirect:/book/" + theme;
			} else {
				Optional<Book> books = bookService.findById(book);
				String username = principal.getName();
				Optional<User> user = userService.findByID(username);
				User userId = user.get();
				cartService.save(cart, number, books.get(), userId);
				return "redirect:/book/detail?book=" + book;
			}
		}

		return "redirect:/book/detail?book=" + book;
	}

	@GetMapping("/delete/{cartId}")
	public String deletebook(@PathVariable(name = "cartId") int cartId) {

		cartService.deletecartId(cartId);
		return "redirect:/cart/";
	}

	@GetMapping("/up/{cartId}/{cartQuantity}")
	public String CountUp(@PathVariable(name = "cartId") int cartId,
			@PathVariable(name = "cartQuantity") int cartQuantity) {

		int UpcartQuantity = cartQuantity + 1;
		cartService.upQuantity(cartId, UpcartQuantity);

		return "redirect:/cart/";
	}

	@GetMapping("/down/{cartId}/{cartQuantity}")
	public String CountDown(@PathVariable(name = "cartId") int cartId,
			@PathVariable(name = "cartQuantity") int cartQuantity) {

		int DowncartQuantity = cartQuantity - 1;
		cartService.DownQuantity(cartId, DowncartQuantity);

		return "redirect:/cart/";
	}

}