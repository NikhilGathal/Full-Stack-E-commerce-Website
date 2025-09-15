package com.jsp.SpringBoot_React.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.CartItemRepository;
import com.jsp.SpringBoot_React.repo.ProductRepository;
import com.jsp.SpringBoot_React.repo.UserRepository;

import org.springframework.transaction.annotation.Transactional;  // ✅ Correct
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	
	public void addToCart(Long userId, Long productId, int quantity) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
		
		
		 int availableStock = product.getRating().getCount();

//		    if (availableStock < quantity) {
//		        throw new RuntimeException("Product is out of stock or insufficient stock available.");
//		    }


		// Check if product is already in the cart
		Optional<CartItem> existingCartItem = user.getCartItems().stream()
				.filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst();

		if (existingCartItem.isPresent()) {
			// Update quantity if the item exists
			CartItem cartItem = existingCartItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItemRepository.save(cartItem);
		} else {
			// Add new cart item
			CartItem newCartItem = new CartItem();
			newCartItem.setProduct(product);
			newCartItem.setQuantity(quantity);
			cartItemRepository.save(newCartItem);
			user.getCartItems().add(newCartItem);
			userRepository.save(user);
		}
	}

	@Transactional
	public void removeFromCart(Long userId, Long productId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

		// Find the cart item to remove
		CartItem cartItemToRemove = user.getCartItems().stream()
				.filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst()
				.orElseThrow(() -> new RuntimeException("Cart item not found for product ID: " + productId));

		user.getCartItems().remove(cartItemToRemove);
		cartItemRepository.delete(cartItemToRemove);
		userRepository.save(user);
	}

	@Transactional
	public void increaseQuantity(Long userId, Long productId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    CartItem cartItem = user.getCartItems().stream()
	            .filter(item -> item.getProduct().getId().equals(productId))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Cart item not found for product ID: " + productId));

	    int currentQuantity = cartItem.getQuantity();
	    int availableStock = cartItem.getProduct().getRating().getCount(); // or getStock()

//	    if (currentQuantity + 1 > availableStock) {
//	        throw new RuntimeException("Cannot increase quantity. Insufficient stock.");
//	    }

	    cartItem.setQuantity(currentQuantity + 1);
	    cartItemRepository.save(cartItem);
	}


	@Transactional
	public void decreaseQuantity(Long userId, Long productId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

		CartItem cartItem = user.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Cart item not found for product ID: " + productId));

		if (cartItem.getQuantity() > 1) {
			cartItem.setQuantity(cartItem.getQuantity() - 1);
			cartItemRepository.save(cartItem);
		} else {
			// Remove item if quantity drops below 1
			user.getCartItems().remove(cartItem);
			cartItemRepository.delete(cartItem);
			userRepository.save(user);
		}
	}
	@Transactional
	public CartItem getCartItem(Long userId, Long productId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    return user.getCartItems().stream()
	        .filter(item -> item.getProduct().getId().equals(productId))
	        .findFirst()
	        .orElse(null); // return null if not found
	}
	

}
