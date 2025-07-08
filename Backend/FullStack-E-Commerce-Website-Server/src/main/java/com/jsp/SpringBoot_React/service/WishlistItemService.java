package com.jsp.SpringBoot_React.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.entity.WishlistItem;
import com.jsp.SpringBoot_React.repo.ProductRepository;
import com.jsp.SpringBoot_React.repo.UserRepository;
import com.jsp.SpringBoot_React.repo.WishlistItemRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemService {

	@Autowired
	private WishlistItemRepository wishlistItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	// Add product to wishlist
	
	@Transactional
	public void addToWishlist(Long userId, Long productId) {
		// Fetch the user and product by their IDs
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

		// Check if product is already in the wishlist
		Optional<WishlistItem> existingWishlistItem = wishlistItemRepository.findAll().stream()
				.filter(wishlistItem -> wishlistItem.getProduct().getId().equals(productId)
						&& wishlistItem.getProduct().getId().equals(productId))
				.findFirst();

		if (existingWishlistItem.isPresent()) {
			throw new RuntimeException("Product already in wishlist.");
		} else {
			// Create a new wishlist item and save it
			WishlistItem newWishlistItem = new WishlistItem();
			newWishlistItem.setProduct(product);
			wishlistItemRepository.save(newWishlistItem);
			 user.getWishlistItems().add(newWishlistItem);
	            userRepository.save(user);
		}
	}
	
	
@Transactional
	public void removeFromWishlist(Long userId, Long productId) {
	    // Fetch the user from the repository
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    // Find the wishlist item to remove
	    WishlistItem wishlistItemToRemove = user.getWishlistItems().stream()
	            .filter(wishlistItem -> wishlistItem.getProduct().getId().equals(productId))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Wishlist item not found for product ID: " + productId));

	    // Remove the item from the user's wishlist
	    user.getWishlistItems().remove(wishlistItemToRemove);

	    // Delete the wishlist item from the repository
	    wishlistItemRepository.delete(wishlistItemToRemove);

	    // Save the updated user object (if necessary)
	    userRepository.save(user);
	}

	
	
	
	
}
