package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
