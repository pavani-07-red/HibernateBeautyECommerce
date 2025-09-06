 package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addSampleProducts() {
        if (productRepository.count() == 0) {
            productRepository.save(new Product("Lipstick", "Kay Beauty", 299.0, "/images/lipstick.jpg"));
            productRepository.save(new Product("Foundation", "Kay Beauty", 899.0, "/images/foundation.jpg"));
            productRepository.save(new Product("Eyeliner", "Kay Beauty", 299.0, "/images/eyeliner.jpg"));
            productRepository.save(new Product("Eyeshadow Palette", "Kay Beauty", 999.0, "/images/eyeshadowpalette.jpg"));
            productRepository.save(new Product("Lip Crayon", "Kay Beauty", 349.0, "/images/lipcrayon.jpg"));
            productRepository.save(new Product("Lip Tint", "Kay Beauty", 499.0, "/images/liptint.jpg"));
            productRepository.save(new Product("Blush", "Kay Beauty", 399.0, "/images/blush.jpg"));
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public List<String> searchProductNames(String query) {
        return productRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }
}


