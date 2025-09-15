package com.jsp.SpringBoot_React.dto;

import java.util.List;

import com.jsp.SpringBoot_React.entity.CartItem;

import lombok.Getter;
import lombok.Setter;


public  class OrderRequest {
    private String username;
    private String order_Id;
    private List<CartItem> cartItems;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOrder_Id() {
		return order_Id;
	}
	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

   
}
