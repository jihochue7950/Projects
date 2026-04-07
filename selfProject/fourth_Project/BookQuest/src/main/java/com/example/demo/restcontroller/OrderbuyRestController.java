package com.example.demo.restcontroller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
public class OrderbuyRestController {

	@Autowired
	private UserService userService;

	@PostMapping("/add/address")
	public String addAdress(@Param("useraddress") String useraddress, @Param("number") int number,
			Principal principal) {
		System.out.println("number" + number);
		String username = principal.getName();
		Optional<User> users = userService.findByID(username);
		User userId = users.get();
		if (number == 2) {
			userId.setAddress2(useraddress);
		} else {
			userId.setAddress3(useraddress);
		}
		userService.saveAddress(userId);

		return useraddress;
	}
}