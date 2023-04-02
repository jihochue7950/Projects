package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.MyPageService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = "/mypage")
public class MyPageController {

	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	MyPageService myPageService;

	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	public String getUserMyPage(Model model, Principal principal) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);

		// 유저의 모든 주문내역을 가져옴.
		List<OrderDetail> orderDetails = orderDetailService.findOrderDetailsByUser(user);

		// 유저가 구매한 모든 책의 카테고리와 권수를 가져옴.
		Map<String, Integer> bookCountsByCategory = myPageService.getBookCountsByCategory(user);
		List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(bookCountsByCategory.entrySet());
		// value 값을 기준으로 내림차순 정렬하는 Comparator 객체 생성
		Comparator<Map.Entry<String, Integer>> valueComparator = (o1, o2) -> o2.getValue().compareTo(o1.getValue());
		// value 값을 기준으로 정렬된 List 반환
		sortedList.sort(valueComparator);
		// 상위 6개의 요소 추출
		List<Map.Entry<String, Integer>> top6List = sortedList.subList(0, Math.min(6, sortedList.size()));
		// model로 넘길 list 생성
		List<Integer> bookCountList = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : top6List) {
			bookCountList.add(entry.getValue());
		}
		
		// 유저가 구매한 모든 책의 카테고리 이름을 가져옴.
		List<String> listCategories = myPageService.findNameByCategory(user);

		// 유저의 모든 결제수단을 가져옴.
		List<Payment> paymentList = paymentService.findPaymentByUser(user);

		// 유저의 배송중, 배송완료의 개수를 가져옴
		int shipping = orderService.countByDeliveryStatus(user, "배송준비중");
		int deliveryCompleted = orderService.countByDeliveryStatus(user, "배송완료");
		
		Integer numbeOfBooksPurchased = orderDetailService.countNumberOfBooksPurchased(user);
		
		List<String> level = new ArrayList<>();
		if (numbeOfBooksPurchased == null ||  numbeOfBooksPurchased < 2) {
			level = Arrays.asList("책린이", "Lv.1", "/images/level=1Lv.png", "/images/clap.png");
		} else if (deliveryCompleted < 6 && deliveryCompleted >= 2) {
			level = Arrays.asList("우수한 독서가", "Lv.2", "/images/level=2Lv.png", "/images/fire.png");
		} else if (deliveryCompleted < 10 && deliveryCompleted >= 6) {
			level = Arrays.asList("성실한 독서가", "Lv.3", "/images/level=3Lv.png", "/images/fire.png");
		} else if (deliveryCompleted < 15 && deliveryCompleted >= 10) {
			level = Arrays.asList("열정적인 다독왕", "Lv.4", "/images/level=4Lv.png", "/images/crown.png");
		} else {
			level = Arrays.asList("살아있는 도서관", "Lv.5", "/images/level=5Lv.png", "/images/crown.png");
		}

		model.addAttribute("user", user);
		model.addAttribute("shipping", shipping);
		model.addAttribute("deliveryCompleted", deliveryCompleted);
		model.addAttribute("level", level);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("bookCountsByCategory", bookCountsByCategory);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("paymentList", paymentList);
	    model.addAttribute("bookCountList", bookCountList);

		return "mypage";
	}
}