/*

package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Sales;
import com.example.demo.service.SalesService;

@Repository
public interface SalesRepository extends PagingAndSortingRepository<Sales, Integer> {
	
	@Query("SELECT s FROM Sales s") //listCheckDeliveryStatus
	SalesService checkDeliveryStatus();


}

*/