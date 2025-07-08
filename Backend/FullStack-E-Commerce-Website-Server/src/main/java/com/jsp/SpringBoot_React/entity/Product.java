package com.jsp.SpringBoot_React.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    private Long id;
    private String title;
    private Double price;
    @Lob
    private String description;
    private String category;
    private String image;

    @Embedded
    private Rating rating;
    // Getters and Setters
}