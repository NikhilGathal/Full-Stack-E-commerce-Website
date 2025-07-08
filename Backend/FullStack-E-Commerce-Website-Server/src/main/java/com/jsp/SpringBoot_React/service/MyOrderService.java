package com.jsp.SpringBoot_React.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.entity.OrderItem;
import com.jsp.SpringBoot_React.repo.CartItemRepository;
import com.jsp.SpringBoot_React.repo.MyOrderRepository;
import com.jsp.SpringBoot_React.repo.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.dto.MyOrderDTO;
import com.jsp.SpringBoot_React.dto.ProductDTO;
import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.MyOrder;
import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.repo.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyOrderService {

	@Autowired
	private MyOrderRepository myOrderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartItemRepository cartItemRepository;
	
	
	 public List<MyOrder> findOrdersByUserId(Long userId) {
	        return myOrderRepository.findOrdersByUserId(userId);
	    }

	@Transactional
	public void clearCart(Long userId) {
		// Find the user by ID
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

		// Remove all cart items from the database
		for (CartItem cartItem : user.getCartItems()) {
			cartItemRepository.delete(cartItem); // Delete each cart item
		}
		// Optionally, clear the list in the User object (not strictly necessary)
		user.getCartItems().clear();
		// Save user after clearing cart if necessary (but typically, cart clearing
		// doesn't require updating the user object)
		userRepository.save(user);
	}

	// Find orders by username


	
//	 public List<MyOrder> findAllOrders() {
//	        return myOrderRepository.findAll();
//	    }
	
	
	public List<MyOrderDTO> findAllOrders() {
	    List<MyOrder> orders = myOrderRepository.findAll();
	    
	    // Convert each MyOrder to MyOrderDTO
	    List<MyOrderDTO> orderDTOs = orders.stream()
	        .map(order -> {
	            // Map each product in the order to a DTO (assuming you have OrderItem and Product)
	            List<ProductDTO> productDTOs = order.getOrderItems().stream()
	                .map(orderItem -> new ProductDTO(
	                    orderItem.getProduct().getId(),
	                    orderItem.getProduct().getTitle(),
	                    orderItem.getProduct().getPrice(),
	                    orderItem.getQuantity()
	                ))
	                .collect(Collectors.toList());

	            // Map the MyOrder to MyOrderDTO with user and product info
	            return new MyOrderDTO(
	                order.getId(),
	                order.getOrderDate(),
	                order.getOrder_Id(),
	                order.getOrderTotal(),
	                order.getTotalQuantity(),
	                order.getUser().getId(), // Get user ID
	                order.getUser().getUsername(), // Get user username
	                order.getUser().getEmail(), // Get user email
	                order.getUser().getAddress(), // Get user address
	                order.getUser().getPhone(), // Get user phone
	                productDTOs // Include list of products in the order
	            );
	        })
	        .collect(Collectors.toList());

	    return orderDTOs;
	}


	
	
	public MyOrder saveOrderForUser(String username, List<CartItem> cartItems ,String orderId) {

		// Fetch the user from the database using the username
		
		System.out.println("cart items we receirved "  + cartItems);

		Optional<User> u = userRepository.findByUsername(username);
		User user = u.get();

		if (user == null) {
			// If user not found, return null or handle accordingly
			return null;
		}

		// Create a new order (MyOrder object)
		MyOrder myOrder = new MyOrder();
		myOrder.setUser(user);
		myOrder.setOrderDate(new java.util.Date()); // Set current date for the order
		 myOrder.setOrder_Id(orderId);
//		// Calculate the total price and quantity from the cart items
		double totalPrice = 0.0;
		int totalQuantity = 0;

		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem cartItem : cartItems) {
			
			  if (cartItem.getProduct() == null) {
			        // Log or throw an exception if Product is null
			        System.out.println("Product is null for cart item: " + cartItem);
			        // Optionally, handle this case (e.g., skip the item or throw an exception)
			    }
			
			totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
			totalQuantity += cartItem.getQuantity();
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct()); // Set product
			orderItem.setQuantity(cartItem.getQuantity()); // Set quantity

			// Add the order item to the list
			orderItems.add(orderItem);
		}
		// Set the order details (total price, total quantity, etc.)
		myOrder.setOrderTotal(totalPrice);
		myOrder.setTotalQuantity(totalQuantity);

		myOrder.setOrderItems(orderItems);
//		myOrder.setTotalQuantity();
//		myOrder.setTotalPrice();
		clearCart(user.getId());
		// Save the order in the database
		return myOrderRepository.save(myOrder);
	}

}
