package com.jsp.SpringBoot_React.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.SpringBoot_React.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByEmail(String email);
}

