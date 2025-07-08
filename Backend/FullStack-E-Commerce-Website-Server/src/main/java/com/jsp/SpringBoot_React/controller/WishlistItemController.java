package com.jsp.SpringBoot_React.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.entity.WishlistItem;
import com.jsp.SpringBoot_React.repo.UserRepository;
import com.jsp.SpringBoot_React.service.WishlistItemService;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistItemController {
	
	 @Autowired
	    private UserRepository userRepository;

    @Autowired
    private WishlistItemService wishlistItemService;

    // Add product to wishlist
    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            wishlistItemService.addToWishlist(userId, productId);
            return ResponseEntity.ok("Product added to wishlist");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to add product to wishlist: " + e.getMessage());
        }
    }

    // Remove product from wishlist
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            wishlistItemService.removeFromWishlist(userId, productId);
            return ResponseEntity.ok("Product removed from wishlist");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to remove product from wishlist: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/wishlist")
    public ResponseEntity<List<WishlistItem>> getWishlistItems(@RequestParam Long userId) {
        System.out.println("Wishlist items retrieved");

        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Return the user's wishlist items
        return ResponseEntity.ok(user.getWishlistItems());
    }
   
    
}