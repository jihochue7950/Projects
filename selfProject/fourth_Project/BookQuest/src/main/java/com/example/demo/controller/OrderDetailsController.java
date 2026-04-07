package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = "/order")
public class OrderDetailsController {

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	BookService bookService;

	@GetMapping("/")
	public String OrderDetails(Model theModel, Principal principal) {

		String userEmail = principal.getName();
		User user = userService.getUserByEmail(userEmail);
		// int userId = user.getUserId();
		List<Order> orders = orderService.findOrderByUser(user);

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for (int i = 0; i < orders.size(); i++) {
			orderDetails.addAll(orderDetailService.findOrderDetailsByOrder(orders.get(i)));

		}
		theModel.addAttribute("orders", orders);
		theModel.addAttribute("orderDetails", orderDetails);

		return "orderDetails";
	}

	@GetMapping("/details/{orderId}")
	public String details(Model theModel, @PathVariable(name = "orderId") int orderId) {

		Order order = orderService.findById(orderId).get();
		List<OrderDetail> orderdetail = orderDetailService.findOrderDetailsByOrder(order);

		theModel.addAttribute("orderdetail", orderdetail);
		theModel.addAttribute("dividedPage", "dividedPage");

		return "orderDetails";
	}

	@PostMapping("/buyselect")
	public String orderBuySelect(@RequestParam(required = false, name = "selectItem") ArrayList<Cart> carts,
			@RequestParam(required = false, name = "book") Book book,
			@RequestParam(required = false, name = "bookquantity") Integer bookquantity, Model model,
			Principal principal, RedirectAttributes redirectAttributes) {

		if (carts != null && book == null) {
			int totalPrice = 0;
			
			
			List<Book> books = new ArrayList<>();			
			for (int i = 0; i < carts.size(); i++) {
				books.add((carts.get(i).getBook()));
			}
			
			
			List<Integer> booksQuantity = new ArrayList<>();
			for (int i = 0; i < carts.size(); i++) {
				booksQuantity.add((carts.get(i).getBookQuantity()));
			}
			
			
			for (int i = 0; i < carts.size(); i++) {
				if (carts.get(i).getBook().getDiscountRate() == 0) {
					int Price = carts.get(i).getBook().getPrice() * carts.get(i).getBookQuantity();
					totalPrice += Price;
				} else if (carts.get(i).getBook().getDiscountRate() != 0) {
					int Price = (int) (carts.get(i).getBook().getPrice()
							* (1 - carts.get(i).getBook().getDiscountRate() * 0.01)) * carts.get(i).getBookQuantity();
					totalPrice += Price;
				}

			}

			model.addAttribute("bookdetail", books);
			String username = principal.getName();
			Optional<User> user = userService.findByID(username);
			List<Payment> paymentList = paymentService.findPaymentByUser(user.get());
			model.addAttribute("bookquantity", booksQuantity);
			model.addAttribute("paymentList", paymentList);
			model.addAttribute("user", user.get());
			model.addAttribute("totalPrice", totalPrice);
			Order order = new Order();
			order.setUser(user.get());
			order.setAddress(user.get().getAddress1());
			model.addAttribute("orders", order);
			model.addAttribute("payment", new Payment());
			return "orderbuy";

		} else if (carts == null && book != null) {

			model.addAttribute("bookdetail", book);
			String username = principal.getName();
			Optional<User> user = userService.findByID(username);
			List<Payment> paymentList = paymentService.findPaymentByUser(user.get());
			model.addAttribute("bookquantity", bookquantity);
			model.addAttribute("paymentList", paymentList);
			model.addAttribute("user", user.get());
			model.addAttribute("totalPrice", book.getPrice()*(100-book.getDiscountRate())/100);
			Order order = new Order();
			order.setUser(user.get());
			order.setAddress(user.get().getAddress1());
			model.addAttribute("orders", order);
			model.addAttribute("payment", new Payment());
			return "orderbuy";
		}

		return "redirect:/book/detail?book=" + book.getBookId();
	}

	@PostMapping("/orderbuy")
	public String orderbuyBook(Model model, Principal principal, 
			@ModelAttribute("orders") Order order,
			@RequestParam(required = false, name = "easyPayment") String easyPayment,
			@RequestParam("totalPrice") int totalPrice,
			@RequestParam(name = "bookId") ArrayList<Book> books,
			@RequestParam(name = "bookquantity") ArrayList<Integer> bookquantity) {

			String userEmail = principal.getName();
			User user = userService.getUserByEmail(userEmail);
				
		if(!(easyPayment == null || easyPayment.isEmpty())) {
			Payment alreadyExistEasyPayment =  paymentService.findEasyPayment(easyPayment);
			if(alreadyExistEasyPayment == null) {
				Payment newEasyPayment = new Payment();
				newEasyPayment.setBank(easyPayment);
				newEasyPayment.setAccountNumber(easyPayment);
				newEasyPayment.setExternalPayment(easyPayment);
				newEasyPayment.setUser(user);
				paymentService.saveEasyPayment(newEasyPayment);
				order.setPayment(newEasyPayment);
			}else {
				order.setPayment(alreadyExistEasyPayment);
			}
		}
		
		if (bookquantity.size() == 1) {
			order.setTotalPrice(totalPrice);
			order.setUser(user);
			orderService.save(order);
			orderDetailService.saveOrderDetails(order, books.get(0), books.get(0).getPrice(), bookquantity.get(0));
			if (cartService.findCartByUserAndBook(user, books.get(0)) != null) {
				List<Cart> cart = new ArrayList<>();
				cart.add(cartService.findCartByUserAndBook(user, books.get(0)));
				cartService.deleteCartByCartId(cart.get(0).getCartId());
			} else {

				return "redirect:/order/";
			}
		} else if (bookquantity.size() > 1) {
			order.setTotalPrice(totalPrice);
			order.setUser(user);
			orderService.save(order);

			List<Cart> cart = new ArrayList<>();
			for (int i = 0; i < books.size(); i++) {

				cart.add(cartService.findCartByUserAndBook(user, books.get(i)));
			}
			for (int i = 0; i < cart.size(); i++) {

				int orderQuantity = cart.get(i).getBookQuantity();
				Book book = cart.get(i).getBook();
				int price = (cart.get(i).getBookQuantity() * cart.get(i).getBook().getPrice());
				orderDetailService.saveOrderDetails(order, book, price, orderQuantity);
			}
			for (int j = 0; j < cart.size(); j++) {
				cartService.deleteCartByCartId(cart.get(j).getCartId());

			}

			return "redirect:/order/";

		}

		return "redirect:/order/";
	}

//	@PostMapping("/buytotal")
//	public String orderBuyAll(Book book, @RequestParam("bookquantity") int bookquantity, Model model,
//			Principal principal, RedirectAttributes redirectAttributes) {
//		System.err.println(bookquantity);
//		int totalPrice = 0;
//		String userEmail = principal.getName();
//		User user = userService.getUserByEmail(userEmail);
//		List<Payment> paymentList = paymentService.findPaymentByUser(user);
//		List<Cart> cartList = cartService.findCartByUser(user);
//
//		for (int i = 0; i < cartList.size(); i++) {
//			int Price = cartList.get(i).getBook().getPrice() * cartList.get(i).getBookQuantity();
//
//			totalPrice += Price;
//		}
//
//		model.addAttribute("paymentList", paymentList);
//		model.addAttribute("cartList", cartList);
//		model.addAttribute("order", new Order());
//		model.addAttribute("totalPrice", totalPrice);
//		return "buyInfoDetailsTotal";
//	}

	@PostMapping("/save")
	public String saveOrder(@ModelAttribute("order") Order order, @RequestParam("totalPrice") int totalPrice,
			Principal principal, @RequestParam("bookId") int bookId, @RequestParam("cartId") int cartId) {

		String userEmail = principal.getName();
		User user = userService.getUserByEmail(userEmail);
		order.setTotalPrice(totalPrice);
		order.setUser(user);
		orderService.save(order);

		Optional<Book> book = bookService.findById(bookId);
		Book book1 = book.get();
		int bookPrice = book.get().getPrice();

		Cart cart = cartService.findById(cartId);

		int orderQuantity = cart.getBookQuantity();
		orderDetailService.saveOrderDetails(order, book1, bookPrice, orderQuantity);
		cartService.deletecartId(cartId);
		return "home";
	}

	@PostMapping("/saveTotal")
	public String saveOrderTotal(@ModelAttribute("order") Order order, Principal principal,
			@RequestParam("totalPrice") int totalPrice) {
		String userEmail = principal.getName();
		User user = userService.getUserByEmail(userEmail);

		order.setTotalPrice(totalPrice);
		order.setUser(user);
		orderService.save(order);

		List<Cart> cart = cartService.findCartByUser(user);

		for (int i = 0; i < cart.size(); i++) {

			int orderQuantity = cart.get(i).getBookQuantity();
			Book book = cart.get(i).getBook();
			int price = (cart.get(i).getBookQuantity() * cart.get(i).getBook().getPrice());
			orderDetailService.saveOrderDetails(order, book, price, orderQuantity);
		}

		cartService.deleteCartByUser(user);

		return "home";
	}

}