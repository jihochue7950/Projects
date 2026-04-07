/*

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Sales;
import com.example.demo.entity.SalesDetail;

@Repository
public interface SalesDetailRepository extends PagingAndSortingRepository<SalesDetail, Integer> {

	@Query("SELECT s FROM SalesDetail s WHERE s.sales =:sales")
	public List<SalesDetail> findBySales(@Param("sales") Sales sales);
	
	

}

*/
