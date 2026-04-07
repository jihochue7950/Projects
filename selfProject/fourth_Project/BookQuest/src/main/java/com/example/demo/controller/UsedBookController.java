/*
package com.example.demo.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Book;
import com.example.demo.entity.Branches;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Sales;
import com.example.demo.entity.SalesDetail;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.BooksBranchService;
import com.example.demo.service.BranchService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.SalesDetailService;
import com.example.demo.service.SalesService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/usedBook")
public class UsedBookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	BranchService branchService;
	
	@Autowired
	BooksBranchService booksbranchService;
	
	@Autowired
	OrderDetailService orderdetailService;
	
	@Autowired
	PaymentService paymentService; 
	
	@Autowired
	SalesService salesService; 
	
	@Autowired
	SalesDetailService salesDetailService; 
	
	@PostMapping("/selluserd")
	public String selluserd(Principal principal, Book book, Model model) {
		String username = principal.getName();
		User user = userService.findByID(username).get();
		  
		List<Branches> branchList = branchService.findAll();
		
		List<Payment> paymentList = paymentService.findPaymentByUser(user);
		
		model.addAttribute("user", user);
		model.addAttribute("paymentList", paymentList);
		model.addAttribute("book", book);
		model.addAttribute("branchList", branchList);

		return "sellbook";
	}

	@PostMapping("/sellbranch")
	public String sellbranch(Principal principal, User user, Book book, Payment payment,@RequestParam("branch") Branches branch, String bookstatus,Model model) {

		if(bookstatus == null) {bookstatus = "확인예정";};
		
		Sales sales = new Sales();
		sales.setAddress(user.getAddress());
		sales.setDeliveryStatus("배송완료");
		LocalDateTime now = LocalDateTime.now();
		sales.setSalesDate(now);
		sales.setTotalPrice((Integer)book.getPrice()/2);
		sales.setBranches(branch);
		sales.setPayment(payment);
		sales.setUser(user);
		salesService.save(sales);
		
		SalesDetail salesDetail = new SalesDetail();
		salesDetail.setSalesQuantity(1);
		salesDetail.setSellPrice((Integer)book.getPrice()/2 * salesDetail.getSalesQuantity());
		salesDetail.setBook(book);
		salesDetail.setSales(sales);
		salesDetail.setBookStatus(bookstatus);

		salesDetailService.save(salesDetail);

		return "redirect:/mypage";
	}
	
}

*/