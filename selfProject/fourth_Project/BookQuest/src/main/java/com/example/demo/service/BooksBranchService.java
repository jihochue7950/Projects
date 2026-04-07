package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.entity.BooksBranch;
import com.example.demo.entity.Branches;
import com.example.demo.entity.Category;
import com.example.demo.repository.BooksBranchRepository;

@Service
@Transactional
public class BooksBranchService {

	@Autowired
	private BooksBranchRepository repo;
	
	public List<BooksBranch> findAll() {
		return (List<BooksBranch>) repo.findAll();
	}

	public List<BooksBranch> findById(Book book) {
		return repo.findByID(book);
	}

	public List<BooksBranch> findByCategory(Category category) {
		return repo.findByCategory(category);
	}
	
	public void save(BooksBranch booksBranch) {
		repo.save(booksBranch);
	}

	public void save(String bookstatus, Branches branch, int bookquantity, Book bookid) {
		BooksBranch bookBranch = new BooksBranch();
		bookBranch.setBook(bookid);
		bookBranch.setStatus(bookstatus);
		bookBranch.setQuantity(bookquantity);
		bookBranch.setBranches(branch);
		repo.save(bookBranch);

	}

	public BooksBranch findByBookBranchInBook(Branches branches, Book book) {
		return repo.findByBookBranchInBook(branches, book);
	}




}