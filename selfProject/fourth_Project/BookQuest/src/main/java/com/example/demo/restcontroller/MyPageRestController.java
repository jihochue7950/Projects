package com.example.demo.restcontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.MyPageService;
import com.example.demo.service.UserService;

@RestController
public class MyPageRestController {

	@Autowired
	UserService userService;

	@Autowired
	MyPageService myPageService;

	// @ResponseBody는 위에 @RestController가 아니라 기본 @Controller 일 때 붙여줌.
	@PostMapping("/bookCountsByCategory")
	public List<Object> getBookCountsByCategory(Principal principal) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		Map<String, Integer> bookCountsByCategory = myPageService.getBookCountsByCategory(user);

		List<Object> bookCountsByCategories = new ArrayList<>();
		bookCountsByCategories.add(bookCountsByCategory.keySet());
		bookCountsByCategories.add(bookCountsByCategory);
		return bookCountsByCategories;
	}

	@PostMapping("/selectedCategory")
	public List<Object> getCategoryByUser(Principal principal, @RequestBody List<String> categories) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		Map<String, Integer> bookCountsByCategory = myPageService.getBookCountsByCategory(user);

		Map<String, Integer> selectedBookCountsByCategory = new HashMap<>();
		for (String category : categories) {
			if (bookCountsByCategory.containsKey(category)) {
				selectedBookCountsByCategory.put(category, bookCountsByCategory.get(category));
			} else {
				selectedBookCountsByCategory.put(category, 0);
			}
		}
		List<Object> selectedBookCountsByCategories = new ArrayList<>();
		selectedBookCountsByCategories.add(selectedBookCountsByCategory.keySet());
		selectedBookCountsByCategories.add(selectedBookCountsByCategory);
		return selectedBookCountsByCategories;
	}

}