package com.jsp.SpringBoot_React.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.SpringBoot_React.entity.TopProduct;

public interface TopProductRepository extends JpaRepository<TopProduct, Long> {
	}


