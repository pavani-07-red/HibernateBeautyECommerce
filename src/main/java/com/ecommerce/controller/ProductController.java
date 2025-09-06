package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public ProductController(ProductRepository p, UserRepository u){this.productRepository=p; this.userRepository=u;}

    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("displayName", session.getAttribute("displayName"));
        return "products";
    }

    @GetMapping("/products/name/{name}")
    public String productByName(@PathVariable String name, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        Product p = productRepository.findByName(name);
        model.addAttribute("selectedProduct", p);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("displayName", session.getAttribute("displayName"));
        return "products";
    }

    @GetMapping("/search")
    @ResponseBody
    public java.util.List<String> search(@RequestParam String query) {
        return productRepository.findByNameContainingIgnoreCase(query).stream().map(Product::getName).toList();
    }
}



