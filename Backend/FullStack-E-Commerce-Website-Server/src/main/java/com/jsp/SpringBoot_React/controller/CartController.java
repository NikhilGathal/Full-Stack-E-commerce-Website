package com.jsp.SpringBoot_React.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.ProductRepository;
import com.jsp.SpringBoot_React.repo.UserRepository;
import com.jsp.SpringBoot_React.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/add")
	public ResponseEntity<String> addToCart(@RequestParam Long userId, @RequestParam Long productId,
			@RequestParam int quantity) {
		
		 User user = userRepository.findById(userId).orElseThrow();
		    Product product = productRepository.findById(productId).orElseThrow();
		    

		    int availableStock = product.getRating().getCount();

		    if (availableStock < quantity) {
		        throw new RuntimeException("Product is out of stock or insufficient stock available.");
		    }
		
		
		System.out.println(userId + "  " + productId + " " + quantity);
		cartService.addToCart(userId, productId, quantity);
		return ResponseEntity.ok("Product added to cart");
	}

	@DeleteMapping("/remove")
	public ResponseEntity<String> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
		cartService.removeFromCart(userId, productId);
		return ResponseEntity.ok("Product removed from cart");
	}

	@PutMapping("/increase")
	public ResponseEntity<String> increaseQuantity(@RequestParam Long userId, @RequestParam Long productId) {
		cartService.increaseQuantity(userId, productId);
		return ResponseEntity.ok("Quantity increased");
	}

	@PutMapping("/decrease")
	public ResponseEntity<String> decreaseQuantity(@RequestParam Long userId, @RequestParam Long productId) {
		cartService.decreaseQuantity(userId, productId);
		return ResponseEntity.ok("Quantity decreased");
	}

	@GetMapping
	public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long userId) {
		System.out.println("cart items retrieved");
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
		return ResponseEntity.ok(user.getCartItems());
	}
	
	@GetMapping("/item")
	public ResponseEntity<CartItem> getCartItem(@RequestParam Long userId, @RequestParam Long productId) {
	    CartItem item = cartService.getCartItem(userId, productId);
	    return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
	}

}