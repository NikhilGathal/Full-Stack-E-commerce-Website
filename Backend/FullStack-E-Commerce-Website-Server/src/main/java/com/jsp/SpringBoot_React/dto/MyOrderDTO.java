package com.jsp.SpringBoot_React.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	    private List<ProductDTO> products; // List of products in the order
}