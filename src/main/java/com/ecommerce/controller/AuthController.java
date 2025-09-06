 package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    public AuthController(UserRepository userRepository){this.userRepository=userRepository;}

    @GetMapping({"/", "/login"})
    public String loginForm(){ return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, HttpSession session, Model model) {
        var opt = userRepository.findByUsername(username);
        if (opt.isPresent()) {
            User user = opt.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("displayName", user.getDisplayName());
            return "redirect:/products";
        } else {
            model.addAttribute("error", "User not found. Try username 'demo'");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){ session.invalidate(); return "redirect:/login"; }
}

