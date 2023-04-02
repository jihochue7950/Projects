package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ApiKey;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;

@Repository
public interface ApiKeyRepository extends CrudRepository<ApiKey, Integer> {

	@Query("SELECT a FROM ApiKey a WHERE a.apiKeyName =:apiKeyName")
	public ApiKey findByName(@Param("apiKeyName")String apiKeyName);


}