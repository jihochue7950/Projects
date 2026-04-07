package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Integer> {

	@Query("SELECT p FROM Payment p WHERE p.user =:user and externalPayment='mycard'")
	public List<Payment> findPaymentByUser(@Param("user") User user);

	@Query("SELECT p FROM Payment p WHERE p.externalPayment =:externalPayment")
	public Payment findEasyPayment(@Param("externalPayment")String easyPayment);

}