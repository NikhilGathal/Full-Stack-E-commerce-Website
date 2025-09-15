package com.jsp.SpringBoot_React.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jsp.SpringBoot_React.entity.CartItem;
import com.jsp.SpringBoot_React.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	 @Query("SELECT c FROM User u JOIN u.cartItems c WHERE u.username = :username")
	    List<CartItem> findCartItemsByUsername(@Param("username") String username);
    
    
	}