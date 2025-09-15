package com.jsp.SpringBoot_React.service;

import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.TopProduct;
import com.jsp.SpringBoot_React.repo.TopProductRepository;

import java.util.List;

@Service
public class TopProductService {

    private final TopProductRepository topProductRepository;

    public TopProductService(TopProductRepository topProductRepository) {
        this.topProductRepository = topProductRepository;
    }

    public List<TopProduct> getAll() {
        return topProductRepository.findAll();
    }

    public void updateTopProducts(List<Long> productIds) {
        topProductRepository.deleteAll();  // clear old
        for (Long id : productIds) {
            topProductRepository.save(new TopProduct(id));
        }
    }
}
