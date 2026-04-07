package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.BooksBranch;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Category;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.BooksBranchService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;

@Controller
@RequestMapping(value = "/book", method = { RequestMethod.GET, RequestMethod.POST })
public class BookController {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private BooksBranchService booksbranchService;

	@GetMapping("")
	public String category_book(Category category, Model model, @Param("keyword") String keyword) {
		List<Category> listCategories = categoryService.findCategory();
		List<Book> books = new ArrayList<Book>();

		if (keyword == null) {
			books.addAll(bookService.listbook(category));
		} else if (keyword != null) {
			books.addAll(bookService.findAll(keyword));
		}

		model.addAttribute("books", books);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("msg", "도서 찾기");
		return "book";
	}

	@GetMapping("/{theme}")
	public String listAllBook(@PathVariable(name = "theme") String theme, Model model) {
		return findBookList(theme, 1, "bookId", "asc", null, null, model);
	}

	@GetMapping("/{theme}/page/{pageNum}")
	public String findBookList(@PathVariable(name = "theme") String theme, @PathVariable(name = "pageNum") int pageNum,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword,
			Category category, Model model) {

		Page<Book> page = null;

		if (category == null || category.getCategoryId() == null) {
			if (theme.equals("fullBook")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword);
				List<Book> books = page.getContent();
				model.addAttribute("books", books);
				model.addAttribute("pagetitle", " 도서찾기");
				model.addAttribute("introduce", "내 취향에 맞는 도서를 찾아보세요.");

			} else if (theme.equals("bestseller")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword);
				List<Book> bestseller = page.getContent();
				model.addAttribute("books", bestseller);
				model.addAttribute("pagetitle", "베스트 셀러");
				model.addAttribute("introduce", "지금 가장 핫한 도서를 만나보세요!");

			} else if (theme.equals("new")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword);
				List<Book> newbooks = page.getContent();
				model.addAttribute("books", newbooks);
				model.addAttribute("pagetitle", "신간 도서");
				model.addAttribute("introduce", "갓 나온 따끈따끈한 도서를 만나보세요!");
			} else if (theme.equals("sale")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword);
				List<Book> saleBook = page.getContent();
				model.addAttribute("books", saleBook);
				model.addAttribute("pagetitle", "할인 도서");
				model.addAttribute("introduce", "저렴한 가격의 도서를 만나보세요!");
			}

		} else if (category != null) {
			if (theme.equals("fullBook")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword, category);
				List<Book> books = page.getContent();
				model.addAttribute("books", books);
				model.addAttribute("pagetitle", " 도서 찾기");
				model.addAttribute("introduce", "내 취향에 맞는 도서를 찾아보세요!");

			} else if (theme.equals("bestseller")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword, category);
				List<Book> bestseller = page.getContent();
				model.addAttribute("books", bestseller);
				model.addAttribute("pagetitle", "베스트 셀러");
				model.addAttribute("introduce", "지금 가장 핫한 도서를 만나보세요!");

			} else if (theme.equals("new")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword, category);
				List<Book> newbooks = page.getContent();
				model.addAttribute("books", newbooks);
				model.addAttribute("pagetitle", "신간 도서");
				model.addAttribute("introduce", "갓 입고된 따끈따끈한 도서를 만나보세요!");
			} else if (theme.equals("sale")) {
				page = bookService.listByPage(theme, pageNum, sortField, sortDir, keyword, category);
				List<Book> saleBook = page.getContent();
				model.addAttribute("books", saleBook);
				model.addAttribute("pagetitle", "할인 도서");
				model.addAttribute("introduce", "저렴한 가격의 도서를 만나보세요!");
			}
		}

		long startCount = (pageNum - 1) * bookService.USERS_PER_PAGE + 1;
		long endCount = startCount + bookService.USERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("totalItems", page.getTotalElements());

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);

		final long PartPage = 5; // 보여줄 컨텐츠의 객수
		long totalPage = page.getTotalPages();

		long endPartPage = (long) Math.ceil((double) pageNum / PartPage) * PartPage;
		long startPartPage = endPartPage - PartPage + 1;
		if (endPartPage > totalPage) {
			endPartPage = totalPage;
		}

		model.addAttribute("startPartPage", startPartPage);
		model.addAttribute("endPartPage", endPartPage);
		model.addAttribute("totalPages", page.getTotalPages());

		List<Category> categoryList = categoryService.findCategory();
		model.addAttribute("categoryList", categoryList);

		List<Category> listCategories = categoryService.findCategory();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("theme", theme);

		return "book";
	}

	@PostMapping("/review")
	public String reviewsave(@RequestParam("book") int book, @ModelAttribute("review") Review review, Model model,
			Principal principal, RedirectAttributes redirectAttributes) {
		if (principal == null) {
			return "redirect:/login";
		}

		Optional<Book> books = bookService.findById(book);
		Book bookId = books.get();
		String username = principal.getName();
		Optional<User> user = userService.findByID(username);
		User userId = user.get();
		reviewService.save(review, userId, bookId);

		redirectAttributes.addFlashAttribute("reviewCrud", "reviewCrud");
		return "redirect:/book/detail?book=" + book;
	}

	@PostMapping("/review/edit/{reviewId}")
	public String reviewEdit(@PathVariable(name = "reviewId") Review review,
			@RequestParam("updateReivew") String updateReivew, @RequestParam("book") int book,
			RedirectAttributes redirectAttributes) {

		reviewService.editReview(review, updateReivew);
		redirectAttributes.addFlashAttribute("reviewCrud", "reviewCrud");
		return "redirect:/book/detail?book=" + book;
	}

	@PostMapping("/review/delete")
	public String reviewDelete(@RequestParam("book") int book, @ModelAttribute("review") Review review, Model model,
			Principal principal, RedirectAttributes redirectAttributes) {

		if (principal == null) {
			return "redirect:/login";
		}

		reviewService.delete(review);

		redirectAttributes.addFlashAttribute("reviewCrud", "reviewCrud");
		return "redirect:/book/detail?book=" + book;
	}

	@GetMapping("/descReview")
	public String descReview(Book book, Model model) {
		List<Category> listCategories = categoryService.findCategory();

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("msg", "평점순");
		return "book";
	}

	@GetMapping("/detail")
	public String bookdetail(Book book, Model model, Principal principal) {
		if (principal == null) {
			model.addAttribute("check", "b");
			model.addAttribute("bookdetail", book);
			model.addAttribute("review", new Review());
			List<Review> review = reviewService.findByBookid(book);
			model.addAttribute("reviewdetail", review);
			model.addAttribute("cart", new Cart());
			List<BooksBranch> bookbranch = booksbranchService.findById(book);
			if (!bookbranch.isEmpty()) {
				int allQuantity = 0;
				for (int i = 0; i < bookbranch.size(); i++) {
					allQuantity += bookbranch.get(i).getQuantity();
				}
				model.addAttribute("allQuantity", allQuantity);
			} else if (bookbranch.isEmpty()) {
				model.addAttribute("outOfStock", "재고수량이 없습니다");
			}

			if (!review.isEmpty()) {
				int bookCount = reviewRepository.countbook(book).intValue();
				Map<Integer, Long> countStarRating = reviewService.countStarRating(book.getBookId());
				Map<Integer, Long> countStarRatingDesc = reviewService.countStarRatingDesc(book.getBookId());

				// 별점이 없는 항목에는 value 0을 넣어줌.
				Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
				setA.removeAll(countStarRatingDesc.keySet());
				setA.forEach(i -> {
					countStarRatingDesc.put(i, (long) 0);
				});

				Map<Integer, Long> sortedMap = new TreeMap<>(Collections.reverseOrder()); // TreeMap 객체 생성 시, 역순으로 정렬하는
																							// Comparator를 전달
				sortedMap.putAll(countStarRatingDesc); // 기존 Map 객체의 모든 요소를 TreeMap 객체로 복사

				model.addAttribute("avgstar", reviewService.avgstar(book));
				model.addAttribute("bookCount", bookCount);
				model.addAttribute("countStarRating", countStarRating);
				model.addAttribute("countStarRatingDesc", sortedMap);
			}

		} else {
			int bookId = book.getBookId();
			String username = principal.getName();
			Optional<User> user = userService.findByID(username);
			User userId = user.get();

			List<Wishlist> wishlistForUser = wishlistService.findByid(userId);

			List<Integer> booksnumber = new ArrayList<>();

			for (int i = 0; i < wishlistForUser.size(); i++) {
				booksnumber.add(wishlistForUser.get(i).getBook().getBookId());
			}

			if (booksnumber.contains(bookId)) {
				model.addAttribute("check", "a");
			} else if (!booksnumber.contains(bookId)) {
				model.addAttribute("check", "b");
			}

			Optional<Book> books = bookService.findById(book.getBookId());
			model.addAttribute("bookdetail", books.get());
			List<Review> review = reviewService.findByBookid(book);
			model.addAttribute("reviewdetail", review);
			if (!review.isEmpty()) {
				model.addAttribute("avgstar", reviewService.avgstar(book));
			}
			model.addAttribute("review", new Review());
			model.addAttribute("cart", new Cart());

			if (!review.isEmpty()) {
				bookService.saveavg(book, reviewService.avgstar(book));
				int bookCount = reviewRepository.countbook(book).intValue();
				Map<Integer, Long> countStarRating = reviewService.countStarRating(book.getBookId());
				Map<Integer, Long> countStarRatingDesc = reviewService.countStarRatingDesc(book.getBookId());

				// 별점이 없는 항목에는 value 0을 넣어줌.
				Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
				setA.removeAll(countStarRatingDesc.keySet());
				setA.forEach(i -> {
					countStarRatingDesc.put(i, (long) 0);
				});

				Map<Integer, Long> sortedMap = new TreeMap<>(Collections.reverseOrder()); // TreeMap 객체 생성 시, 역순으로 정렬하는
																							// Comparator를 전달
				sortedMap.putAll(countStarRatingDesc); // 기존 Map 객체의 모든 요소를 TreeMap 객체로 복사

				model.addAttribute("avgstar", reviewService.avgstar(book));
				model.addAttribute("bookCount", bookCount);
				model.addAttribute("countStarRating", countStarRating);
				model.addAttribute("countStarRatingDesc", sortedMap);
			}
			model.addAttribute("review", new Review());
			model.addAttribute("cart", new Cart());
			List<BooksBranch> bookbranch = booksbranchService.findById(book);

			if (!bookbranch.isEmpty()) {
				int allQuantity = 0;
				for (int i = 0; i < bookbranch.size(); i++) {
					allQuantity += bookbranch.get(i).getQuantity();
				}
				model.addAttribute("allQuantity", allQuantity);
				// model.addAttribute("bookbranch", bookbranch);
			} else if (bookbranch.isEmpty()) {
				model.addAttribute("outOfStock", "재고수량이 없습니다");
			}
		}

		return "bookdetail";
	}
}