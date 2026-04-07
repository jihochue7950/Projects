package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> findCategory() {		
		Iterable<Category> AllCategory= repo.findAll();		
		List<Category> CategoryList = new ArrayList<Category>(); 

		for(Category category : AllCategory) {
			if(category.getParent() == null) {
	
				String name = "----" + category.getName()+"----";
				CategoryList.add(Category.copyFull(category, name));
				List<Category> childern = category.getChildren();
				
				for (Category subCategory : childern) {
					CategoryList.add(Category.copyFull(subCategory));
				}
			}
		}
		return CategoryList;
	}
	

	
}