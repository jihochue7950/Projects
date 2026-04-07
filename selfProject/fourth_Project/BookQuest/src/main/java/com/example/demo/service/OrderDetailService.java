package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderDetailRepository;

@Service
@Transactional
public class OrderDetailService {

	@Autowired
	private OrderDetailRepository repo;
	
	public List<OrderDetail> findOrderDetailsByOrder(Order order) {
		return repo.findOrderDetailsByOrder(order);
	}
	
	public List<OrderDetail> findOrderDetailsByUser(User user) {
		return repo.findOrderDetailsByUser(user);
	}

	public void saveOrderDetails(Order order, Book book1, int bookPrice, int orderQuantity) {
		OrderDetail orderdetail = new OrderDetail();
		orderdetail.setBook(book1);
		orderdetail.setOrder(order);
		orderdetail.setPrice(bookPrice);
		orderdetail.setOrderQuantity(orderQuantity);
		repo.save(orderdetail);
	}

	public Integer countNumberOfBooksPurchased(User user) {
		return repo.countNumberOfBooksPurchased(user);
	}




}
