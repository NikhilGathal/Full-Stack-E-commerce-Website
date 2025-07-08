package com.jsp.SpringBoot_React.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.SpringBoot_React.entity.User;
import com.jsp.SpringBoot_React.entity.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
	
}

