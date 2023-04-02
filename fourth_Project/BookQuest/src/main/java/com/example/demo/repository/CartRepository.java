package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;

@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart, Integer> {

	@Query("SELECT u FROM User u WHERE u.email =:email")
	public User getUserByEmail(@Param("email") String email);

	@Query("SELECT c FROM Cart c WHERE c.user =:user")
	public List<Cart> findCartByUser(User user);

	@Modifying
	@Query("DELETE FROM Cart c WHERE c.user =:user")
	public void deleteByUser(User user);

	/*
	 * @Query("UPDATE Users u set u.enabled = ?2 where u.id = ?1") public void
	 * updateEnabled(int a,boolean eanbled);
	 */

	@Modifying
	@Query("UPDATE Cart c set c.bookQuantity = ?2 where c.cartId = ?1")
	public void upQuantity(int cartId, int UpcartQuantity);

	@Modifying
	@Query("UPDATE Cart c set c.bookQuantity = ?2 where c.cartId = ?1")
	public void downQuantity(int cartId, int downcartQuantity);

	@Query("SELECT c FROM Cart c WHERE c.user =:user")
	public Page<Cart> findAll(Pageable pageable, User user);

	@Query("SELECT c FROM Cart c WHERE c.user=:user and c.book=:book")
	public Cart findCartByUserAndBookId(User user, Book book);

	@Modifying
	@Query("DELETE FROM Cart c WHERE c.cartId =:carts")
	public void deleteCartByCartId(int carts);

}