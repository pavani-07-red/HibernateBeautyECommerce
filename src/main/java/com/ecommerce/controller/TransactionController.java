 package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Transaction;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class TransactionController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/transaction/buy")
    public String buyNow(@RequestParam Long productId,
                         @RequestParam String name,
                         @RequestParam String email,
                         Model model) {

        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            Transaction txn = new Transaction();
            txn.setProduct(product);
            txn.setQuantity(1);
            txn.setCustomerName(name);
            txn.setEmail(email);
            txn.setTotalAmount(product.getPrice());
            txn.setTransactionTime(LocalDateTime.now());

            transactionRepository.save(txn);
            model.addAttribute("transaction", txn);
        }
        return "transaction"; // transaction.html
    }
}


