package com.jsp.SpringBoot_React.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Rating {
    private Double rate;
    private Integer count;

    // Getters and Setters
}
