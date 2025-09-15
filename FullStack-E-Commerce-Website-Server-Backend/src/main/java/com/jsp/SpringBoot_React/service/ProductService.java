package com.jsp.SpringBoot_React.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.repo.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void saveAll(List<Product> products) {
        try {
            productRepository.saveAll(products);
        } catch (Exception e) {
            // Log the error for debugging
            System.out.println("Error saving products: " + e.getMessage());
            throw e;  // Re-throw the exception after logging it
        }
      
    }
    
    public void saveProduct(Product product) {
        try {
            productRepository.save(product); // Save a single product
        } catch (Exception e) {
            throw new RuntimeException("Error while saving the product: " + e.getMessage());
        }
    }
    
    


    // Update an existing product
    public Product updateProduct(Long productId, Product updatedProduct) {
        // Find the product by id
        Optional<Product> existingProductOptional = productRepository.findById(productId);
        
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            
            // Update fields as needed
            existingProduct.setTitle(updatedProduct.getTitle());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setImage(updatedProduct.getImage());
            existingProduct.setRating(updatedProduct.getRating());

            return productRepository.save(existingProduct);
        } else {
            // Product not found
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    // Delete a product
    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    // Get a single product by id
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    
    public Product updateStock(Long id, int delta) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        int newCount = product.getRating().getCount() + delta;
        if (newCount < 0) newCount = 0;
        product.getRating().setCount(newCount);
        return productRepository.save(product);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
