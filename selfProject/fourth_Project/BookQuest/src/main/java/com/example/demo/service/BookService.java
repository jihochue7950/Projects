package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.repository.BookRepository;

@Service
@Transactional
public class BookService {
	public final int ADMIN_PER_PAGE =15;
	public final int USERS_PER_PAGE = 10;

	@Autowired
	private BookRepository repo;

	public List<Book> listbook(Category category) {
		return repo.findByCategoryId(category);
	}

	public List<Book> newbooks() {
		return repo.finByNewBook();
	}

	public List<Book> findAll() {
		return (List<Book>) repo.findAll();
	}

	public List<Book> bestseller() {
		return repo.bestseller();
	}

	public List<Book> findDiscountBooks() {
		return repo.findDiscountBooks();
	}

	public Long countTotalBooks() {
		return repo.countTotalBooks();
	}

	public Long countBestBooks() {
		return repo.countBestBooks();
	}

	public Long countTotaldiscountBook() {
		return repo.countTotaldiscountBook();
	}

	public Page<Book> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}
	
	
	public Page<Book> listByPage(String theme, int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
		if (keyword != null) {
			if (theme.equals("fullBook")) {
				return repo.findAll(keyword, pageable);
			} else if (theme.equals("bestseller")) {
				return repo.bestseller(keyword, pageable);
			} else if (theme.equals("new")) {
				return repo.findByNewBook(keyword, pageable);
			} else if (theme.equals("sale")) {
				return repo.findBySaleBook(keyword, pageable);
			}
		}
		if (theme.equals("fullBook")) {
			return repo.findAll(pageable);
		} else if (theme.equals("bestseller")) {
			return repo.bestseller(pageable);
		} else if (theme.equals("new")) {
			return repo.findByNewBook(pageable);
		} else if (theme.equals("sale")) {
			return repo.findBySaleBook(pageable);
		}
		return repo.findAll(pageable);
	}
	
	public Page<Book> listByPage(String theme, int pageNum, String sortField, String sortDir, String keyword,
			Category category) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
		if (keyword != null) {
			if (theme.equals("fullBook")) {
				return repo.findAll(keyword, category, pageable);
			} else if (theme.equals("bestseller")) {
				return repo.bestseller(keyword, category, pageable);
			} else if (theme.equals("new")) {
				return repo.findByNewBook(keyword, category, pageable);
			} else if (theme.equals("sale")) {
				return repo.findBySaleBook(keyword, category, pageable);
			}
		}
		if (theme.equals("fullBook")) {
			return repo.findAll(category, pageable);
		} else if (theme.equals("bestseller")) {
			return repo.bestseller(category, pageable);
		} else if (theme.equals("new")) {
			return repo.findByNewBook(category, pageable);
		} else if (theme.equals("sale")) {
			return repo.findBySaleBook(category, pageable);
		}
		return repo.findAll(pageable);
	}

	public List<Book> findRandomBooks() {
		return (List<Book>) repo.findRandomBooks();
	}

	public List<Book> findAll(String keyword) {
		return repo.findAll(keyword);
	}

	public Book save(Book registering) {
		return repo.save(registering);
	}

	public Optional<Book> findById(int bookId) {
		return repo.findById(bookId);
	}

	public void deleteById(Integer bookId) throws Exception {
		if (repo.findById(bookId) == null) {
			throw new Exception("Could not find any user with ID " + bookId);
		}
		repo.deleteById(bookId);
	}

	public void saveavg(Book book, Float avgstar) {
		book.setAvgStar(avgstar);
		repo.save(book);
	}

	public List<Book> findByBranch(Integer BranchId) {
		return repo.findByBranch(BranchId);
	}

	public List<Book> sortprice() {
		return repo.sortprice();
	}

	public List<Book> sortTitle() {
		return repo.sortTitle();
	}


}