package com.streaming.controller;

import com.streaming.model.User;
import com.streaming.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final AuthService authService;

    public ProfileController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.getCurrentUser(auth.getName());
        model.addAttribute("user", user);
        return "profile/index";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               RedirectAttributes redirectAttributes) {
        try {
            authService.changePassword(currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("success", "Mot de passe modifié avec succès");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }

    @GetMapping("/history")
    public String viewHistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.getCurrentUser(auth.getName());
        model.addAttribute("watchHistory", user.getWatchlist());
        return "profile/history";
    }
} 