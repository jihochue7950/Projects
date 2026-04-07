package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.User;

@Repository
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Integer> {

	// 상세내역 table을 통해 주문내역을 가져옴.
	@Query("SELECT o FROM OrderDetail o WHERE o.order =:order")
	public List<OrderDetail> findOrderDetailsByOrder(@Param("order") Order order);

	// 특정 유저의 주문 상세내역을 가져옴.
	@Query("SELECT od FROM OrderDetail od JOIN FETCH od.book WHERE od.order.user = :user")
	public List<OrderDetail> findOrderDetailsByUser(@Param("user") User user);

	// 유저가 구매한 책의 권수와 카테고리를 가져옴.
	@Query("SELECT c, SUM(od.orderQuantity) FROM Category c JOIN Book b ON c = b.category "
			+ "LEFT JOIN OrderDetail od ON b = od.book " 
			+ "JOIN od.order o " + "WHERE o.user = :user " 
			+ "GROUP BY c ORDER BY SUM(od.orderQuantity) DESC")
	public List<Object[]> findCategoriesAndBookCountsByUser(@Param("user") User user);

	// 유저가 구매한 책의 카테고리를 가져옴.
	@Query("SELECT DISTINCT c.name " + "FROM Category c " + "JOIN Book b ON c = b.category "
			+ "JOIN OrderDetail od ON b = od.book " 
			+ "JOIN od.order o " + "WHERE o.user = :user " + "GROUP BY c")
	public List<String> findCategoriesByUser(@Param("user") User user);

	//유저가 구매한 책의 총 권수를 가지고 옴.
	@Query("SELECT SUM(od.orderQuantity) FROM Order o JOIN o.orderDetail od WHERE o.user = :user AND o.deliveryStatus = '배송완료'")
	public Integer countNumberOfBooksPurchased(@Param("user") User user);



	
}