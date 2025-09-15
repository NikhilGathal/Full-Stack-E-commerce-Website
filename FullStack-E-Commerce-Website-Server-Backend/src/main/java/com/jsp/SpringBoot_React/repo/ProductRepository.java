package com.jsp.SpringBoot_React.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

}