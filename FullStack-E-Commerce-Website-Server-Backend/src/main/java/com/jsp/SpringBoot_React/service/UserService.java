package com.jsp.SpringBoot_React.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.jsp.SpringBoot_React.dto.UserDto;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.CartItemRepository;
import com.jsp.SpringBoot_React.repo.MyOrderRepository;
import com.jsp.SpringBoot_React.repo.UserRepository;
import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private MyOrderRepository myOrderRepository;

	public String getAdminUsername() {
		Optional<User> admin = userRepository.findByIsAdmin(1); // Fetch the user with isAdmin = 1 (admin)
		return admin.map(User::getUsername).orElse("No admin user found");
	}

	// Signup - Register a new user
	public User signup(User user) {
		// No password encoding, store password as-is
		return userRepository.save(user);
	}

	// Login - Authenticate user by username and password
	public String login(String username, String password) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent() && user.get().getPassword().equals(password)) {
			return "Login successful";
		} else {
			return "Invalid credentials";
		}
	}

	// Update User Details (Only allow email change, other details can be updated)
//	public User updateUser(Long id, User user) {
//		Optional<User> existingUser = userRepository.findById(id);
//		if (existingUser.isPresent()) {
//			User updatedUser = existingUser.get();
//			if (!updatedUser.getEmail().equals(user.getEmail())) {
//				updatedUser.setEmail(user.getEmail());
//			}
//			updatedUser.setUsername(user.getUsername());
//			updatedUser.setPhone(user.getPhone());
//			updatedUser.setAddress(user.getAddress());
//			return userRepository.save(updatedUser);
//		}
//		throw new RuntimeException("User not found");
//	}

	// Forgot Password - Send reset password email link
	public String forgotPassword(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			// Logic to send reset password email (or you can simulate it)
			return "Password reset link sent to " + username;
		}
		return "Username not found";
	}

	public boolean isAdminExists() {
		return userRepository.countByIsAdmin(1) > 0;
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username); // Returns Optional<User>
	}

	// Find all users with their orders (using a custom query)
	public List<User> findAllUsersWithOrders() {
		return userRepository.findAll(); // Will return all users and their orders because of the bidirectional
											// relationship
	}

}