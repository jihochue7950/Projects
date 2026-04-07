package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.ApiKey;
import com.example.demo.entity.Book;
import com.example.demo.entity.Branches;
import com.example.demo.entity.Category;
import com.example.demo.model.ChatGPT;
import com.example.demo.repository.ApiKeyRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.BooksBranchService;
import com.example.demo.service.BranchService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OrderDetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	BookService bookService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BranchService branchService;

	@Autowired
	OrderDetailService orderdetailService;

	@Autowired
	BooksBranchService booksBranchService;
	
	@Autowired
	ApiKeyRepository apiKeyRepository;

	@GetMapping("")
	public String viewHomePage(Authentication authentication, Model model, Category category) {
		final int showBookMain = 5;

		List<Book> randomBooks = bookService.findRandomBooks();
		Long totalBooks = bookService.countTotalBooks();
		model.addAttribute("randomBooks",totalBooks < showBookMain ? randomBooks : randomBooks.subList(0, showBookMain));
		
		 List<Book> bestseller = bookService.bestseller();
		 Long totalBestseller = bookService.countBestBooks();
		 model.addAttribute("bestseller", totalBestseller < showBookMain ? bestseller : bestseller.subList(0, showBookMain));

		List<Book> newbooks = bookService.newbooks();
		model.addAttribute("newbooks", totalBooks < showBookMain ? newbooks : newbooks.subList(0, showBookMain));
		
		List<Book> discountBook = bookService.findDiscountBooks();
		Long totalDiscountBook = bookService.countTotaldiscountBook();
		model.addAttribute("discountBook",totalDiscountBook < showBookMain ? discountBook : discountBook.subList(0, showBookMain));
		
		String[][] recommendationList = { { "추천마법사의 선택", "/book/fullBook", "randomBooks", "/images/book.png"},
							                { "베스트 셀러", "/book/bestseller", "bestseller", "/images/fire.png"},
							                { "갓 입고된 따끈 따끈 도서", "/book/new", "newbooks", "/images/bread.png"},
							                { "이 주의 특가 도서 모음ZIP", "/book/sale", "discountBook", "/images/file.png"} };
		
		 if (authentication == null) {
			recommendationList = Arrays.copyOfRange(recommendationList, 1, recommendationList.length);
		}

		model.addAttribute("recommendationList", recommendationList);
		return "home";
	}

	// <div th:replace="commonspace :: menu" />에서는 먹히지 않음
	// @GetMapping("/common")
	// public String commonspace(Model model) {
	// List<Branches> branchList = branchService.findAll();
	// model.addAttribute("branchList", branchList);
	// return "commonspace";
	// }

	@GetMapping("/informationBranch/{id}")
	public String branchInformation(@PathVariable("id") Integer id, Model model) {
		Branches branchInformation = branchService.finById(id);
		List<Book> branchBooks = bookService.findByBranch(id);
		List<Category> listCategories = categoryService.findCategory();

		model.addAttribute("branchInformation", branchInformation);
		model.addAttribute("books", branchBooks);
		model.addAttribute("listCategories", listCategories);
		return "informationBranch";
	}

	@GetMapping("/customerServiceCenter")
	public String customerServiceCenter(Model themodel,@RequestParam(required = false)String question, HttpSession session) {
		
		List<String[]> previousQuestionsAndAnswers = (List<String[]>) session.getAttribute("previousQuestionsAndAnswers");

		//처음 접속 때 성능 향상	
	    if (question == null || question.isEmpty()) {
	        session.setAttribute("previousQuestionsAndAnswers", previousQuestionsAndAnswers);
	        themodel.addAttribute("previousQuestionsAndAnswers", previousQuestionsAndAnswers);
			return "customerServiceCenter";
	    }

		
		RestTemplate restTemplate = new RestTemplate();
			//"curie", "babbage", "ada", "davinci"
		String model = "text-davinci-003";
		ApiKey apikey = apiKeyRepository.findByName("OpenAI");
		String url = "https://api.openai.com/v1/completions";

		//HttpHeaders 오브젝트 생성
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + apikey.getApiKeyValue());
		httpHeaders.set("Content-Type", "application/json");
		//httpHeaders.setBearerAuth(OPEN_AI_KEY);
		//httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		//HttpBody 오브젝트 생성
		Map<String, Object> params = new HashMap<>();
		params.put("prompt", question);
		params.put("model", model);
			//생성할 답변의 창의성 및 다양성을 조절하는 매개 변수입니다. 
		params.put("temperature", 0.6);
		params.put("max_tokens", 1500);
		
		//HttpHeaders와 HttpBody를 오브젝트로 담기
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, httpHeaders);
		
		//Http 요청하기 - Post방식으로 -그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	
		//response.getBody();에서 받아온 값이 json임으로 자바오브젝트로 변경함
		ObjectMapper objectMapper = new ObjectMapper();
		ChatGPT chatGPT = null;
		
		try {
			chatGPT = objectMapper.readValue(response.getBody(), ChatGPT.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//답변은 받아 왔음으로 http문서로 보내기 작업을 해야함	   
		if (previousQuestionsAndAnswers == null) {
		    previousQuestionsAndAnswers = new ArrayList<>();
		}
	    
	    if (question != null && !question.isEmpty()) {
	        String answer = chatGPT.getChoices().get(0).getText();
	        String[] newQnA = new String[]{question, answer};
	        previousQuestionsAndAnswers.add(newQnA);
	        session.setAttribute("previousQuestionsAndAnswers", previousQuestionsAndAnswers);
	    }
	    themodel.addAttribute("previousQuestionsAndAnswers", previousQuestionsAndAnswers);
	
		return "customerServiceCenter";
	}
	
	@PostMapping("/clearSession")
	public String clearSession(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    session.removeAttribute("previousQuestionsAndAnswers");
	    return "redirect:/customerServiceCenter";
	}
	

}
