package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartController(ProductRepository p, CartRepository c, UserRepository u){
        this.productRepository = p;
        this.cartRepository = c;
        this.userRepository = u;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return "redirect:/products"; // or some error page

        CartItem item = new CartItem(user, product, 1);
        cartRepository.save(item);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        List<CartItem> items = cartRepository.findByUser(user);
        double total = items.stream().mapToDouble(i -> i.getQuantity() * i.getProduct().getPrice()).sum();

        model.addAttribute("cartItems", items);
        model.addAttribute("total", total);
        model.addAttribute("displayName", session.getAttribute("displayName"));
        return "cart";
    }

    @PostMapping("/cart/reset")
    public String clearCart(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        cartRepository.deleteByUser(user);
        return "redirect:/cart";
    }

    @PostMapping("/buy/now")
    public String buyNow(@RequestParam Long productId, HttpSession session) {
        session.setAttribute("checkoutProductId", productId);
        return "redirect:/checkout";
    }
}
