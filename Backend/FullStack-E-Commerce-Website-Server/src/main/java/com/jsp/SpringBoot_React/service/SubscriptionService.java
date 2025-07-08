package com.jsp.SpringBoot_React.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.Subscription;
import com.jsp.SpringBoot_React.repo.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    public boolean saveSubscription(String email) {
    	String cleanEmail = email.replace("\"", "");
        if (!repository.existsByEmail(cleanEmail)) {
            Subscription subscription = new Subscription();
            subscription.setEmail(cleanEmail);
            repository.save(subscription);
            System.out.println("Email Subscribed");
            return true; // Email saved
        }
        return false; // Email already exists
    }
    
    
    public List<Subscription> fetchAllEmails() {
        return repository.findAll();
//                .stream()
//                .map(subscription -> subscription.getEmail())
//                .collect(Collectors.toList());
    }
}

