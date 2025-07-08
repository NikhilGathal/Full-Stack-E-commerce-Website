package com.jsp.SpringBoot_React.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jsp.SpringBoot_React.entity.Subscription;
import com.jsp.SpringBoot_React.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping
    public String addSubscription(@RequestBody String email) {
        boolean isSaved = service.saveSubscription(email);
        return isSaved ? "Subscription added successfully!" : "Email already subscribed!";
    }
    
    @GetMapping
    public List<Subscription> getAllEmails() {
        return service.fetchAllEmails();
    }
}

