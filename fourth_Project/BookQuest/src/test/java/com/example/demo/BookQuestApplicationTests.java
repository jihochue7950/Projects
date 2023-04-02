package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.demo.entity.ApiKey;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.ApiKeyRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@DataJpaTest(showSql = true)
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
//에디터에서 데이터베이스로 데이터 넣기가 가능해짐
@Rollback(false) 
class BookQuestApplicationTests {

	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private BranchRepository BrRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private OrderDetailRepository odRepo;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private ApiKeyRepository apiRepo;
	
	@Autowired
	private PaymentRepository payRepo;
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "test1234";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
	}

	@Test
	public void testUpdateEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userRepo.findByUserId(1);
		String rawPassword = "1";
		user.setPassword(passwordEncoder.encode(rawPassword));
	}
	

	
	@Test
	public void TestPage(){
//		
//		int pageNumber = 1;
//		int pageSize = 1;
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<Object> page = odRepo.bestseller(pageable);
//		System.err.println(page);
//		
//		List<Object> list = odRepo.bestseller();
//		System.err.println(list);
		
//		System.err.println(odRepo.bestseller());
		
	}
	
	@Test
	public void deleteReview() {
		int id = 22;
		Review reviews = new Review(id);
		reviewRepo.delete(reviews);
	}
	
	@Test
	public void findByApiKey() {	
	ApiKey apikey = apiRepo.findByName("OpenAI");
		System.out.println("apikey = " + apikey);
	}
	
	@Test
	public void alreadyExistEasyPayment() {
		String easyPayment = "kakao";
		Payment alreadyExistEasyPayment =  payRepo.findEasyPayment(easyPayment);
		System.err.println(alreadyExistEasyPayment);
		System.err.println(alreadyExistEasyPayment == null);

	}
}
