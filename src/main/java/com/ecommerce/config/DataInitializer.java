 package com.ecommerce.config;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    // Constructor to inject UserRepository
    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String demoUsername = "demo";

        // Check if demo user exists already
        if (userRepository.findByUsername(demoUsername).isEmpty()) {
            User demoUser = new User();
            demoUser.setUsername(demoUsername);
            demoUser.setPassword("demo123");  // Plain text password; change if you use encoding
            demoUser.setEmail("demo@example.com");
            demoUser.setDisplayName("Demo User");

            userRepository.save(demoUser);
            System.out.println("Demo user created.");
        } else {
            System.out.println("Demo user already exists.");
        }
    }
}


