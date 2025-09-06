 package com.ecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String email;

    private int quantity;

    private double totalAmount;

    private LocalDateTime transactionTime;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;  // <-- Added User relationship

    public Transaction() {}

    public Transaction(String customerName, String email, int quantity, double totalAmount, LocalDateTime transactionTime, Product product, User user) {
        this.customerName = customerName;
        this.email = email;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.transactionTime = transactionTime;
        this.product = product;
        this.user = user;  // initialize user
    }

    public Long getId() { return id; }

    public String getCustomerName() { return customerName; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalAmount() { return totalAmount; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getTransactionTime() { return transactionTime; }

    public void setTransactionTime(LocalDateTime transactionTime) { this.transactionTime = transactionTime; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}

