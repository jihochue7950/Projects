package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

public class BookQuestDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new BookQuestDetails(user);
		}
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}