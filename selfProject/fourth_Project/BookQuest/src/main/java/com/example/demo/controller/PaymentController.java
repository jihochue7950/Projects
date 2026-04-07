
package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.FileUploadUtil;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	PaymentService paymentService;

	@GetMapping("/")
	public String page(Model theModel, Payment payment, Principal principal) {
		Payment payments = new Payment();

		User user = userService.findByEmail(principal.getName());
		List<Payment> paymentList = paymentService.findPaymentByUser(user);
		theModel.addAttribute("payment", payments);
		theModel.addAttribute("user", user);
		theModel.addAttribute("paymentList", paymentList);
		return "payment";
	}

	@GetMapping("/paymentadd")
	public String paymentadd(Model theModel, Payment payment) {
		Payment payments = new Payment();
		theModel.addAttribute("payment", payments);
		return "paymentadd";
	}

	@PostMapping("/save")
	public String Save(Payment payment, Model themodel, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		paymentService.savepayment(payment, user);
		List<Payment> paymentList = paymentService.findPaymentByUser(user);
		themodel.addAttribute("user", user);
		themodel.addAttribute("paymentList", paymentList);

		return "redirect:/payment/";

	}

//	지불수단에는 업데이트 기능이 없어야함
//	@GetMapping("/update/{paymentId}")
//	public String edit(@PathVariable(name = "paymentId") int paymentId, Model model, RedirectAttributes rttr)
//			throws Exception {
//
//		Payment payment = paymentService.get(paymentId);
//		model.addAttribute("payment", payment);
//		return "paymentadd";
//
//	}

	@GetMapping("/delete/{paymentId}")
	public String deleteById(@PathVariable(name = "paymentId") int paymentId, Model themodel, Principal principal) {

		User user = userService.findByEmail(principal.getName());
		List<Payment> paymentList = paymentService.findPaymentList();
		themodel.addAttribute("user", user);
		themodel.addAttribute("paymentList", paymentList);
		paymentService.delete(paymentId);

		return "redirect:/payment/";
	}

}