package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository repo;

	public List<Order> findOrderByUser(User user) {
		return repo.findOrderByUser(user);
	}

	public void save(Order order) {
		order.setOrderDate(LocalDateTime.now());
		order.setUser(order.getUser());
		order.setDeliveryStatus("배송준비중");
		repo.save(order);
	}

	public void saveTotal(Order order, int price, User user) {
		order.setOrderDate(LocalDateTime.now());
		order.setUser(user);
		order.setTotalPrice(price);
		order.setDeliveryStatus("배송준비중");
		repo.save(order);
	}
	
	public void changeDeliveryStatus(Order order) {
		repo.save(order);
	}

	public Optional<Order> findById(int orderId) {
		return repo.findById(orderId);
	}

	public List<Order> findByStatus(String deliveryStatus) {
		return repo.findByStatus(deliveryStatus);
	}

	public List<Order> findAll() {
		return (List<Order>) repo.findAll();
	}

	public int countByDeliveryStatus(User user, String deliveryStatus) {
		return  repo.countByDeliveryStatus(user, deliveryStatus);
	}


}