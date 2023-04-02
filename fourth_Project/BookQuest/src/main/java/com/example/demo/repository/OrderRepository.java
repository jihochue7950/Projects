package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {

	@Query("SELECT o FROM Order o WHERE o.user =:user")
	public List<Order> findOrderByUser(@Param("user") User user);
	
	@Query("SELECT o FROM Order o WHERE o.deliveryStatus =:deliveryStatus")
	public List<Order> findByStatus(@Param("deliveryStatus") String deliveryStatus);	

	@Query("SELECT count(o) FROM Order o WHERE o.user =:user and o.deliveryStatus =:deliveryStatus")
	public int countByDeliveryStatus(@Param("user") User user, @Param("deliveryStatus") String deliveryStatus);

}