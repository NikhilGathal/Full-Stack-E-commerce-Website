package com.jsp.SpringBoot_React.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.SpringBoot_React.entity.Product;
import com.jsp.SpringBoot_React.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/saveall")
	public ResponseEntity<String> saveProducts(@RequestBody List<Product> products) {
		productService.saveAll(products);
		return ResponseEntity.ok("Products saved successfully");
	}

	@PostMapping("/save")
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		try {
			productService.saveProduct(product); // Saving a single product
			return ResponseEntity.ok("Product saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error saving product: " + e.getMessage());
		}
	}

	// Update an existing product
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
		Product updated = productService.updateProduct(id, updatedProduct);
		return ResponseEntity.ok(updated);
	}

	// Delete a product
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	// Get a single product by id
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		Product product = productService.getProduct(id);
		return ResponseEntity.ok(product);
	}

	// Get all products (Optional)
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@PutMapping("/stock/{id}/{delta}")
	public ResponseEntity<Product> updateProductStock(@PathVariable Long id, @PathVariable int delta) {
	    Product updated = productService.updateStock(id, delta);
	    return ResponseEntity.ok(updated);
	}
}
