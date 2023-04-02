package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.BooksBranch;
import com.example.demo.entity.Branches;
import com.example.demo.entity.Category;

@Repository
public interface BooksBranchRepository extends PagingAndSortingRepository<BooksBranch, Integer> {

	@Query("SELECT br FROM BooksBranch br WHERE br.book=:book")
	List<BooksBranch> findByID(@Param("book") Book book);

	@Query("SELECT bb FROM BooksBranch bb JOIN bb.book b WHERE b.category =:category")
	List<BooksBranch> findByCategory(@Param("category") Category category);

	@Query("SELECT br FROM BooksBranch br WHERE br.branches=:branches and br.book=:book")
	BooksBranch findByBookBranchInBook(@Param("branches")Branches branches, @Param("book")Book book);

}