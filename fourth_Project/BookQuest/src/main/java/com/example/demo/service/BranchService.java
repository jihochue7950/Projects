package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.entity.Branches;
import com.example.demo.repository.BranchRepository;

@Service
@Transactional
public class BranchService {
	
	@Autowired
	private BranchRepository repo;

	public List<Branches> findAll() {
		return (List<Branches>) repo.findAll();
	}

	public Branches finById(Integer id) {
		return repo.findById(id).get();
	}


}