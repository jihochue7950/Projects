
package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.FileUploadUtil;
import com.example.demo.entity.User;
import com.example.demo.model.KakaoProfile;
import com.example.demo.model.OAuthToken;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/sign")
public class SignUpController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String signUpPage(Model theModel, Principal principal) {

		/*
		 * System.out.println("test====="+principal.getName()); String email =
		 * principal.getName(); List<User> UserInfo = userService.UserInfo(email);
		 * System.out.println(UserInfo);
		 */
		// theModel.addAttribute("userInfo", UserInfo);
		User user = new User();
		theModel.addAttribute("user", user);
		return "signUpPage";
	}

	@PostMapping("/save")
	public String Save(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
			user.setPhoto("");
			userService.save(user);
			redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		return "redirect:/";
	}
	
	@GetMapping("/kakao")
	public String kakaoLogin(String code, RedirectAttributes redirectAttributes) {
		//코드값을 가져올수 있다면 인증은완료 되어 있는 상태
		//System.out.println("카카오 인증 완료:코드값" + code);
 		
		//토큰을 받아올 차례 
		//Post 방식으로 key=value데이터를 요청(카카오쪽으로)
		RestTemplate restTemplate = new RestTemplate();
		
		//HttpHeaders 오브젝트 생성
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "9746c6479208e369f43bef1b3570952b");
		params.add("redirect_uri", "http://localhost:4444/BookQuest/sign/kakao");
		params.add("code", code);
		
		//HttpHeaders와 HttpBody를 하나의 오브젝트로 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, httpHeaders);
		
		//Http 요청하기 - Post방식으로 -그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = restTemplate.exchange(
				"https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		//response.getBody();에서 받아온 값이 json임으로 자바오브젝트로 변경함
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		//System.out.println("카카오 엑세스 토큰" +oauthToken.getAccess_token());
		/*-------------------------------------------------------------------*/
		
		//토큰을 통한 사용자 정보 조회(Post)
		//Post 방식으로 key=value데이터를 요청(카카오쪽으로)
		RestTemplate restTemplateProfile = new RestTemplate();
		
		//HttpHeaders 오브젝트 생성
		HttpHeaders httpHeadersProfile = new HttpHeaders();
		httpHeadersProfile.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		httpHeadersProfile.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeaders와 HttpBody를 하나의 오브젝트로 담기 
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
				new HttpEntity<>(httpHeadersProfile);
		
		//Http 요청하기 - Post방식으로 -그리고 response 변수의 응답 받음.
		ResponseEntity<String> responseProfile = restTemplateProfile.exchange(
				"https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);
		
		//responseProfile.getBody();에서 받아온 값이 json임으로 자바오브젝트로 변경함
		ObjectMapper objectMapperProfile = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapperProfile.readValue(responseProfile.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//받아온 프로필에서 정보 가져오기
		String kakaoLoginUser = kakaoProfile.getKakao_account().getProfile().getNickname()+"_"+kakaoProfile.getId();
		String kakaoLoginUserName = kakaoProfile.getKakao_account().getProfile().getNickname();
		String kakaoPassword = "kakaoPassword";
		
		//회원 가입 여부 확인하기
		User kakaoUser = userService.getUserByEmail(kakaoLoginUser);
		
		//신규회원이면 회원 가입
		if(kakaoUser == null) {
			kakaoUser = new User();
			kakaoUser.setEmail(kakaoLoginUser);
			kakaoUser.setPassword(kakaoPassword);
			kakaoUser.setAddress1("카카오주소");
			kakaoUser.setName(kakaoLoginUserName);
			kakaoUser.setPhone("카카오전화번호");
			userService.saveKakao(kakaoUser);
		}
		
		//로그인 처리하기
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(kakaoUser.getEmail(), kakaoPassword);
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		redirectAttributes.addFlashAttribute("message", "카카오계정으로 로그인에 성공 하였습니다.");
			
		return "redirect:/";
	}
	
}