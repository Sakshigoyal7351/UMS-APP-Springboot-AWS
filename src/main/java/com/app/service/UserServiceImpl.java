package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.entity.User;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	

	@Override
	public User getUserById(int userId) {
		
		return userRepository.findById(userId).get();
	}
	
	@Override
	public User updateUser(User user) {
		
		return userRepository.save(user);
	}
}
