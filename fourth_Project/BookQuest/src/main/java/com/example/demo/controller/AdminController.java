package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.FileUploadUtil;
import com.example.demo.entity.Book;
import com.example.demo.entity.BooksBranch;
import com.example.demo.entity.Branches;
import com.example.demo.entity.Category;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.service.BookService;
import com.example.demo.service.BooksBranchService;
import com.example.demo.service.BranchService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	BookService bookService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BranchService branchService;
	
	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	BooksBranchService booksBranchService;

	@GetMapping("/newBookRegistration")
	public String newBookregistration(Model model) {
		LocalDate now = LocalDate.now();
		Book registering = new Book();
		registering.setPublicationDate(now);
		model.addAttribute("registering", registering);

		List<Category> categoryList = categoryService.findCategory();
		model.addAttribute("categoryList", categoryList);

		return "newBookRegisteringAndRevising";
	}

	@PostMapping("/saveBookInformation")
	public String saveBookInformation(Book registering, @RequestParam("uploadBookCover") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {

		if (!multipartFile.isEmpty()) {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			registering.setImage(fileName);

			Book saveBook = bookService.save(registering);
			String uploadDir = "bookCover/" + saveBook.getCategory().getName();

			// FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			bookService.save(registering);
		}
		redirectAttributes.addFlashAttribute("message","<" + registering.getTitle() + "> has been saved successfully.");
		return "redirect:/admin/editBookInformation";

	}

	@GetMapping("/editBookInformation")
	public String adminBookListFirst(Model model) {
		return editBookInformation(1, "bookId", "asc", null, null, model);
	}

	@GetMapping("/editBookInformation/page/{pageNum}")
	public String editBookInformation(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword, Category category, Model model) {
		
		Page<Book> page = null;
		
		if (category == null || category.getCategoryId() == null) {
			page = bookService.listByPage(pageNum, sortField, sortDir, keyword);
			List<Book> listBooks = page.getContent();
			model.addAttribute("books", listBooks);
		} else if (category != null) {
			page = bookService.listByPage("fullBook",pageNum, sortField, sortDir, keyword, category);
			List<Book> listBooks = page.getContent();
			model.addAttribute("books", listBooks);
		}

		long startCount = (pageNum - 1) * bookService.ADMIN_PER_PAGE + 1;
		long endCount = startCount + bookService.ADMIN_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("category", category);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);

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
		return "editBookInformation";
	}

	@GetMapping("/editBookInformation/{bookId}")
	public String editBookInformation(@PathVariable("bookId") Integer bookId, RedirectAttributes redirectAttributes,
			Model model) {

		try {
			Book registering = bookService.findById(bookId).get();
			model.addAttribute("registering", registering);
		} catch (Exception e) {

			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());
			return "redirect:/users/";
		}
		List<Category> categoryList = categoryService.findCategory();
		model.addAttribute("categoryList", categoryList);

		model.addAttribute("pageTilte", "Edit User (ID : " + bookId + ")");

		return "newBookRegisteringAndRevising";
	}

	@GetMapping("/deleteBookInformation/{bookId}")
	public String deleteBookInformation(@PathVariable("bookId") Integer bookId, RedirectAttributes redirectAttributes) {
		Book book = bookService.findById(bookId).get();
		try {
			bookService.deleteById(bookId);
			String uploadDir = "bookCover/" + book.getCategory().getName();
			FileUploadUtil.delete(uploadDir, book.getImage());
			redirectAttributes.addFlashAttribute("message", "<" + book.getTitle() + "> has been deleted successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/admin/editBookInformation";
	}

	@RequestMapping(value = { "/checkDeliveryStatus" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String checkDeliveryStatus(@RequestParam(required = false, name = "bookToChangeShippingStatus") Order[] orders,
			@RequestParam(defaultValue = "전체", name ="deliveryStatus") String deliveryStatus, Model model) {
		
		
		
		if(orders != null) {
			for (Order order : orders) {
				//체크박스로 넘겨 받은 데이터중 배송준비중인 order만 골라서
				if (order.getDeliveryStatus().equals("배송준비중")) {
					//order 밑에 있는 orderDetail상태를 수정하기 위해서
					List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailsByOrder(order);
					//4개의 지점중 하나를 골라서
					Random rand = new Random();
					Branches branches = new Branches(rand.nextInt(4) + 1);
						for (OrderDetail orderDetail : orderDetailList) {
							//한 지점에서 한책의 정보를 가지고 오고
							BooksBranch bookstoreInventory = booksBranchService.findByBookBranchInBook(branches, orderDetail.getBook());
							//지점에 있는 재고에서 주문한 수량만큼 감소시킨다.
							bookstoreInventory.setQuantity(bookstoreInventory.getQuantity()-orderDetail.getOrderQuantity());
							booksBranchService.save(bookstoreInventory);	
						}
					//한권한권 제고를 줄이고, 성공했다면 배송완료로 변경
					order.setDeliveryStatus("배송완료");
					orderService.changeDeliveryStatus(order);	
				}
			}
		}
		
		
		
		List<Order> orderList = orderService.findByStatus(deliveryStatus);
		if(deliveryStatus.equals("전체")) {
			orderList = orderService.findAll();
		}
		model.addAttribute("orderList", orderList);
	
		List<BooksBranch> booksBranchList = booksBranchService.findAll();
		model.addAttribute("booksBranchList", booksBranchList);
		//model.addAttribute("deliveryStatus", deliveryStatus);
		
		
		
		
	
		return "checkDeliveryStatus";
	}
}