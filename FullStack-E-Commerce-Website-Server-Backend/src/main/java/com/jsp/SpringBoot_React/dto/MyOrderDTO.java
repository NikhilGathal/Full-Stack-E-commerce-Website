package com.jsp.SpringBoot_React.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class MyOrderDTO {
	 private Long id;
	    private Date orderDate;
	    private String order_Id; 
	    private Double orderTotal;
	    private Integer totalQuantity;
	    private Long userId;
	    private String username;
	    private String email;
	    private String address;
	    private String phone;
	    private int rating;
	    private List<ProductDTO> products; // List of products in the order
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getOrderDate() {
			return orderDate;
		}
		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}
		public String getOrder_Id() {
			return order_Id;
		}
		public void setOrder_Id(String order_Id) {
			this.order_Id = order_Id;
		}
		public Double getOrderTotal() {
			return orderTotal;
		}
		public void setOrderTotal(Double orderTotal) {
			this.orderTotal = orderTotal;
		}
		public Integer getTotalQuantity() {
			return totalQuantity;
		}
		public void setTotalQuantity(Integer totalQuantity) {
			this.totalQuantity = totalQuantity;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public int getRating() {
			return rating;
		}
		public void setRating(int rating) {
			this.rating = rating;
		}
		public List<ProductDTO> getProducts() {
			return products;
		}
		public void setProducts(List<ProductDTO> products) {
			this.products = products;
		}
		public MyOrderDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public MyOrderDTO(Long id, Date orderDate, String order_Id, Double orderTotal, Integer totalQuantity,
				Long userId, String username, String email, String address, String phone, int rating,
				List<ProductDTO> products) {
			super();
			this.id = id;
			this.orderDate = orderDate;
			this.order_Id = order_Id;
			this.orderTotal = orderTotal;
			this.totalQuantity = totalQuantity;
			this.userId = userId;
			this.username = username;
			this.email = email;
			this.address = address;
			this.phone = phone;
			this.rating = rating;
			this.products = products;
		}
	    
	    
}