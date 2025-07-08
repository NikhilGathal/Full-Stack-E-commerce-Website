package com.jsp.SpringBoot_React.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.Feedback;
import com.jsp.SpringBoot_React.repo.FeedbackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository repository;

	public void saveFeedback(Feedback feedback) {
		repository.save(feedback);
	}

	public List<Feedback> fetchAllFeedbacks() {
		return repository.findAll();
	}
}
