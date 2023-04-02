package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Branches;

@Repository
public interface BranchRepository extends PagingAndSortingRepository<Branches, Integer> {

}
