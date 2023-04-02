package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.email =:email")
	public User getUserByEmail(@Param("email") String email);

//  @Query("UPDATE User u SET u.password = ?2 WHERE u.userId = ?1")
//  @Modifying
//  public void updatePasswordById(Integer userId, String password);
	public User findByUserId(Integer userId);

	@Query("SELECT u FROM User u WHERE u.email =:username")
	public Optional<User> finByID(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.email =:name")
	public User findByEmail(@Param("name") String name);

}