package com.app.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserRepository userRepository;
	
	private UserService userService;

	public MainController(UserRepository userRepository, UserService userService) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
@GetMapping("/")
	
	public String show()
	{
		return "home";
		
	}
	
	//handler for showing the registration page
	
	@GetMapping("/registration")
	public String registrationPage(User user)
	{
		return "registrationPage";
	}
	
	@GetMapping("/login")
	public String loginPage(User user)
	{
		return "loginPage";
	}
	
	
	
	
	
	//handler method to handle list Users and return mode and view  
	
		@GetMapping("/users")
		public String listUsers(Model model)
		{
			model.addAttribute("users", userService.getAllUser());
			return "users";
		}
	
	//handler for registration process
	
	
	@PostMapping("/register")
	
	public String register(@ModelAttribute("user") User user)
	{
		System.out.println(user);
		
		String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(encodedPassword);
		
		
		userRepository.save(user);
		
//		return "welcome";
//		return "Data Saved Successfully !!";
		return "redirect:/users";
	}
	
	
	//handler for login process
	
	
	@PostMapping("/login")
	
	public String loginProcess(@RequestParam("username") String username, 
			@RequestParam("password") String password)
	{
		User dbUser = userRepository.findByUsername(username);
		Boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());
		if(isPasswordMatch)
		{
			return "users";
		}
		else
		{
			return "Failed to login";
		}
		
		
	}
	
	
	
	
	
	
	
	
	@GetMapping("/users/edit/{userId}")
	public String editUserForm(@PathVariable int userId, Model model)
	{
		model.addAttribute("user",userService.getUserById(userId));
		return "edit_user";
	}
	
	@PostMapping("/users/{userId}")
	public String updateUser(@PathVariable int userId, 
			@ModelAttribute("user") User user, 
			Model model)
	{
		//get User
		
		User existingUser =userService.getUserById(userId);
		existingUser.setUserId(userId);
		existingUser.setName(user.getName());
		existingUser.setUsername(user.getUsername());
		existingUser.setGender(user.getGender());
		existingUser.setDob(user.getDob());
		existingUser.setTechnology(user.getTechnology());
		
		//save user
		
		userService.updateUser(existingUser);
		return "redirect:/users";
		
	}
	
	
}
