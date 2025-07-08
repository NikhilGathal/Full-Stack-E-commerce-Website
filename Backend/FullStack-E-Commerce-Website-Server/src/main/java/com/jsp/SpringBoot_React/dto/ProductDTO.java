package com.jsp.SpringBoot_React.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;

  
}

