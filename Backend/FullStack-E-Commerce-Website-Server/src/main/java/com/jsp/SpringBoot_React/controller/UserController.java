package com.jsp.SpringBoot_React.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.SpringBoot_React.dto.LoginResponse;
import com.jsp.SpringBoot_React.dto.UserDto;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.UserRepository;
import com.jsp.SpringBoot_React.service.SubscriptionService;
import com.jsp.SpringBoot_React.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		// Fetch user along with cartItems and their associated products (eagerly
		// loaded)
//		Optional<User> userOpt = userRepository.findByUsernameWithCartItems(username);
		Optional<User> userOpt = userRepository.findByUsername(username);
		System.out.println("method is called " + username);
		if (userOpt.isPresent()) {
			System.out.println("Data Retrieved success");
			return ResponseEntity.ok(userOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/admin-username")
	public String getAdminUsername() {
		return userService.getAdminUsername(); // Fetch the admin username
	}

	// Signup API (Register a new user)
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			// Create an empty User or a User with some error information
			User errorUser = new User();
			errorUser.setEmail(user.getEmail()); // Set the email for context (optional)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorUser); // Return 400 with an empty user
																					// object
		}

		// Check if the username already exists
		if (userRepository.existsByUsername(user.getUsername())) {
			// Create an empty User or a User with some error information
			User errorUser = new User();
			errorUser.setUsername(user.getUsername()); // Set the username for context (optional)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorUser); // Return 400 with an empty user
																					// object
		}

		if (user.getIsAdmin() == 1 && userRepository.countByIsAdmin(1) > 0) {
			User errorUser = new User();
			errorUser.setIsAdmin(1); // Optional: Set isAdmin to indicate that the error is related to admin creation
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorUser); // Return 400 with an empty user
																					// object
		}

		int isAdminValue = user.getIsAdmin() == 1 ? 1 : 0;
		user.setIsAdmin(isAdminValue);

		if (user.getIsAdmin() == 0) {
			subscriptionService.saveSubscription(user.getEmail());
		}

		System.out.println("Received isAdmin: " + user.getIsAdmin());
		return ResponseEntity.ok(userService.signup(user));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		System.out.println(username + " " + password + " has attempted to log in");

		// Check if the username exists
		Optional<User> existingUser = userRepository.findByUsername(username);
		if (existingUser.isEmpty()) {
			// Username does not exist
			System.out.println("Invalid Username");
			LoginResponse loginResponse = new LoginResponse("Username is not valid", false);
			return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
		}

		// Check if the password matches
		User foundUser = existingUser.get();
		if (!foundUser.getPassword().equals(password)) {
			// Password does not match
			System.out.println("Invalid Password");
			LoginResponse loginResponse = new LoginResponse("Password is not valid", false);
			return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
		}

		// Login is successful
		boolean isAdmin = foundUser.getIsAdmin() == 1; // Check if the user is an admin
		System.out.println("Login successful");

		LoginResponse loginResponse = new LoginResponse("Login successful", isAdmin);
		return ResponseEntity.ok(loginResponse);

	}

//	@PutMapping("/{id}")
//	public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User updatedUser) {
//		System.out.println("id " + id);
//		System.out.println("add is " + updatedUser.getAddress());
//		Optional<User> userOpt = userRepository.findById(id); // Search by ID
//		if (userOpt.isPresent()) {
//			User existingUser = userOpt.get();
//
//			// Update fields
//			existingUser.setUsername(updatedUser.getUsername());
//			existingUser.setPassword(updatedUser.getPassword());
//			existingUser.setPhone(updatedUser.getPhone());
//			existingUser.setEmail(updatedUser.getEmail());
//			existingUser.setAddress(updatedUser.getAddress());
//			existingUser.setIsAdmin(updatedUser.getIsAdmin());
//			System.out.println("updated success ");
//			userRepository.save(existingUser); // Save updated user
//			return ResponseEntity.ok(existingUser);
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//	}
	
	
	
	
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
	    Optional<User> existingUserOpt = userRepository.findById(id);
	    
	    if (!existingUserOpt.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    User existingUser = existingUserOpt.get();

	    // Check if the updated username or email already exists in another user
	    Optional<User> existingUserWithSameUsername = userRepository.findByUsername(updatedUser.getUsername());
	    Optional<User> existingUserWithSameEmail = userRepository.findByEmail(updatedUser.getEmail());

	    if (existingUserWithSameUsername.isPresent() && !existingUserWithSameUsername.get().getId().equals(id)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"username\": \"Username already exists\"}");
	    }

	    if (existingUserWithSameEmail.isPresent() && !existingUserWithSameEmail.get().getId().equals(id)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"email\": \"Email already exists\"}");
	    }

	    // Update user details
	    existingUser.setUsername(updatedUser.getUsername());
	    existingUser.setPassword(updatedUser.getPassword());
	    existingUser.setPhone(updatedUser.getPhone());
	    existingUser.setEmail(updatedUser.getEmail());
	    existingUser.setAddress(updatedUser.getAddress());
	    existingUser.setIsAdmin(updatedUser.getIsAdmin());

	    userRepository.save(existingUser);
	    
	    return ResponseEntity.ok(existingUser);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@DeleteMapping("/username/{username}")
	public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
		Optional<User> userOpt = userRepository.findByUsername(username); // Search by username
		if (userOpt.isPresent()) {
			System.out.println("user deleted successfully");
			userRepository.delete(userOpt.get()); // Delete the user directly
			return ResponseEntity.ok("User deleted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}

	@GetMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String username) {
		Optional<User> userOpt = userRepository.findByUsername(username);

		if (userOpt.isPresent()) {
			// User found, return a success message
			return ResponseEntity.ok("User found");
		} else {
			// User not found, return an error message
			return ResponseEntity.status(HttpStatus.OK).body("User not found");
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String username,
			@RequestParam String newPassword) {

		Optional<User> userOpt = userRepository.findByUsername(username);
		Map<String, String> response = new HashMap<>();

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setPassword(newPassword); // Set new password
			userRepository.save(user); // Save the updated password in DB

			response.put("message", "Password reset successfully.");
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "User not found.");
			return ResponseEntity.badRequest().body(response);
		}
	}

}