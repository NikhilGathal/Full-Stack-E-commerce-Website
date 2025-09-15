package com.jsp.SpringBoot_React.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.SpringBoot_React.entity.TopProduct;
import com.jsp.SpringBoot_React.service.TopProductService;

import java.util.List;

@RestController
@RequestMapping("/api/top-products")
public class TopProductController {

    private final TopProductService topProductService;

    public TopProductController(TopProductService topProductService) {
        this.topProductService = topProductService;
    }

    // Fetch selected top product IDs
    @GetMapping
    public ResponseEntity<List<TopProduct>> getTopProducts() {
        return ResponseEntity.ok(topProductService.getAll());
    }

    // Save/update top product IDs
    @PostMapping
    public ResponseEntity<String> saveTopProducts(@RequestBody List<Long> productIds) {
        topProductService.updateTopProducts(productIds);
        return ResponseEntity.ok("Top products updated!");
    }
}
