 package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.TransactionRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CheckoutController {
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CheckoutController(ProductRepository productRepository, TransactionRepository transactionRepository,
                              CartRepository cartRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        Long checkoutProductId = (Long) session.getAttribute("checkoutProductId");
        if (checkoutProductId != null) {
            Product product = productRepository.findById(checkoutProductId).orElse(null);
            model.addAttribute("checkoutItem", product);
        } else {
            List<CartItem> items = cartRepository.findByUser(user);
            model.addAttribute("cartItems", items);
        }

        model.addAttribute("displayName", session.getAttribute("displayName"));
        return "checkout";
    }

    @PostMapping("/checkout/confirm")
    public String confirmCheckout(@RequestParam(required = false) Long productId,
                                  @RequestParam String name,
                                  @RequestParam String email,
                                  HttpSession session,
                                  Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        if (productId != null) {
            // Checkout a single product (Buy Now)
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                Transaction txn = new Transaction();
                txn.setUser(user);
                txn.setProduct(product);
                txn.setQuantity(1);
                txn.setTotalAmount(product.getPrice());
                txn.setTransactionTime(LocalDateTime.now());
                transactionRepository.save(txn);
            }
        } else {
            // Checkout all cart items
            List<CartItem> items = cartRepository.findByUser(user);
            for (CartItem item : items) {
                Transaction txn = new Transaction();
                txn.setUser(user);
                txn.setProduct(item.getProduct());
                txn.setQuantity(item.getQuantity());
                txn.setTotalAmount(item.getQuantity() * item.getProduct().getPrice());
                txn.setTransactionTime(LocalDateTime.now());
                transactionRepository.save(txn);
            }
            cartRepository.deleteByUser(user);
        }

        model.addAttribute("message", "Checkout successful!");
        return "transaction";
    }
}
