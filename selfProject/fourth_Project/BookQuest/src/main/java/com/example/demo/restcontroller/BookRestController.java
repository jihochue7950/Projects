package com.example.demo.restcontroller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ReviewService;

@RestController
public class BookRestController {
	@Autowired
	ReviewService reviewService;

	@PostMapping("/countStarRating")
	@ResponseBody
	public Map<Integer, Long> countStarRating(@RequestBody Integer bookId) {
		System.out.println("bookId: " + bookId);
		Map<Integer, Long> countStarRating = reviewService.countStarRating(bookId);
		System.out.println(countStarRating.keySet());

		Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
		setA.removeAll(countStarRating.keySet());
		setA.forEach(i -> {
			countStarRating.put(i, (long) 0);
		});

		return countStarRating;
	}
}
