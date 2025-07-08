package com.jsp.SpringBoot_React.dto;

import java.util.List;

import com.jsp.SpringBoot_React.entity.CartItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class OrderRequest {
    private String username;
    private String order_Id;
    private List<CartItem> cartItems;

   
}
