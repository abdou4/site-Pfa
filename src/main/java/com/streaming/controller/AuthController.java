package com.streaming.controller;

import com.streaming.model.User;
import com.streaming.service.AuthService;
import com.streaming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "success", required = false) String success,
                              Model model) {
        if (error != null) {
            model.addAttribute("error", "Email ou mot de passe incorrect");
        }
        if (success != null) {
            model.addAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/auth/login?error";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Une erreur s'est produite lors de l'inscription. Veuillez réessayer.");
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                    @RequestParam String email,
                                    @RequestParam String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            userService.registerUser(user);
            return "redirect:/auth/login?success";
        } catch (Exception e) {
            return "redirect:/auth/register?error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/?logout";
    }
} 