package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@GetMapping("/delete/{userId}")
	public String deleteById(@PathVariable(name = "userId") int userId, RedirectAttributes redirectAttributes) {
		
	    //인증 정보 초기화, 로그아웃 역활
	    SecurityContextHolder.clearContext();

	   //카카오 유저면 연결끊기도 같이 진행함
	   if(userService.findById(userId).get().getRole().equals("Kakao")) {
	   
	   	}
	   
	    userService.delete(userId);
	    redirectAttributes.addFlashAttribute("message", "회원탈퇴에 성공 하였습니다.");
	    
		return "redirect:/";
	}

	@GetMapping("/update/{userId}")
	public String editUser(@PathVariable(name = "userId") int userId, Model model, RedirectAttributes rttr)
			throws Exception {

		User user = userService.get(userId);
		model.addAttribute("user", user);
		if (user.getPassword() != null) {
			String check = "yes";
			model.addAttribute("check", check);
		}
		model.addAttribute("edit", "a");

		return "signUpPage";

	}
	
	@PostMapping("/save/{userId}")
	public String update(Model model, Principal principal, @RequestParam("userId") Integer userId) {
		model.addAttribute("user", userId);
		return "signUpPage";
	}

}