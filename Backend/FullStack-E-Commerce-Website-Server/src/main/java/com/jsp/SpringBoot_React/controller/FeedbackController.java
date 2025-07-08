package com.jsp.SpringBoot_React.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jsp.SpringBoot_React.entity.Feedback;
import com.jsp.SpringBoot_React.entity.Subscription;
import com.jsp.SpringBoot_React.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService service;

    @PostMapping
    public String addFeedback(@RequestBody Feedback feedback) {
        service.saveFeedback(feedback);
        return "Feedback added successfully!";
    }
    
    @GetMapping
    public List<Feedback> getAllEmails() {
        return service.fetchAllFeedbacks();
    }
}

