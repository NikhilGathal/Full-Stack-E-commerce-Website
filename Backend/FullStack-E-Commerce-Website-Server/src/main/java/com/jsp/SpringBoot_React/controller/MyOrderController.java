package com.jsp.SpringBoot_React.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.SpringBoot_React.dto.MyOrderDTO;
import com.jsp.SpringBoot_React.dto.OrderRequest;
import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.entity.OrderItem;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.CartItemRepository;
import com.jsp.SpringBoot_React.service.CartService;
import com.jsp.SpringBoot_React.service.MyOrderService;
import com.jsp.SpringBoot_React.service.ProductService;
import com.jsp.SpringBoot_React.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class MyOrderController {

	@Autowired
	private MyOrderService myOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductService productService;
	
	 @GetMapping("/allUsersWithOrders")
	 public ResponseEntity<List<MyOrderDTO>> getAllUsersWithOrders() {
	     try {
	         List<MyOrderDTO> orders = myOrderService.findAllOrders();
	         return ResponseEntity.ok().body(orders);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle error
	     }
	 }
	 
	 @GetMapping("/user/{id}")
	 public ResponseEntity<List<MyOrder>> getAllOrdersForUser(@PathVariable Long id) {

	     System.out.println("Fetching orders for user with ID: " + id);

	     try {
	         List<MyOrder> orders = myOrderService.findOrdersByUserId(id);
	         if (orders != null && !orders.isEmpty()) {
	             return ResponseEntity.ok().body(orders);
	         } else {
	             return ResponseEntity.ok(Collections.emptyList()); // No orders found
	         }
	     } catch (Exception e) {
	         return ResponseEntity.status(500).body(null); // Handle error
	     }
	 }

	// 3. Fetch all users along with their orders
	

	@PostMapping("/place")
	public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {

		String username = orderRequest.getUsername();
		String orderId = orderRequest.getOrder_Id();
		List<CartItem> cartItems = cartItemRepository.findCartItemsByUsername(username);

		if (username == null || username.isEmpty()) {
			return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
		}

		if (cartItems == null || cartItems.isEmpty()) {
			return new ResponseEntity<>("Cart items are empty", HttpStatus.BAD_REQUEST);
		}

		// Call the service to save the order
		MyOrder savedOrder = myOrderService.saveOrderForUser(username, cartItems,orderId);

		if (savedOrder != null) {
			return new ResponseEntity<>("Order placed successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Failed to place the order", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Helper method to convert the cartItems string into a List<CartItem>
	
	
	@DeleteMapping("/cancel/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable String orderId) {
	    try {
	        Optional<MyOrder> optionalOrder = myOrderService.findOrderByOrder_Id(orderId);
	        if (!optionalOrder.isPresent()) {
	            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
	        }

	        MyOrder order = optionalOrder.get();

	        // ✅ Loop through each item to restore quantity
	        for (OrderItem item : order.getOrderItems()) {
	            Long productId = item.getProduct().getId();
	            int quantity = item.getQuantity();

	            // Increase the count using productService
	            productService.updateStock(productId, quantity);
	        }

	        // ✅ Delete the order after restoring stock
	        myOrderService.deleteOrder(order);

	        return new ResponseEntity<>("Order cancelled and stock restored successfully", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Failed to cancel order", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PutMapping("/rating/{orderId}")
	public ResponseEntity<String> updateOrderRating(@PathVariable String orderId, @RequestBody Map<String, Integer> payload) {
	
	 try {
	        Optional<MyOrder> optionalOrder = myOrderService.findOrderByOrder_Id(orderId);
	        if (!optionalOrder.isPresent()) {
	            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
	        }
	        MyOrder order = optionalOrder.get();
	        int rating = payload.get("rating");
	        myOrderService.updatemyorder(order, rating);
	        return ResponseEntity.ok("Rating updated successfully");
	      
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Failed to update order", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
}
}