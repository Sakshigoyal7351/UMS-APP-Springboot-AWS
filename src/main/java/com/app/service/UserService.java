package com.app.service;

import java.util.List;

import com.app.entity.User;

public interface UserService {
	
List<User> getAllUser();
	
	User getUserById(int userId);
	
	User updateUser(User user);

}
