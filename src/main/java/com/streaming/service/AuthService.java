package com.streaming.service;

import com.streaming.model.User;
import com.streaming.model.Role;
import com.streaming.repository.UserRepository;
import com.streaming.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user, String confirmPassword) {
        logger.info("Tentative d'inscription pour l'utilisateur: {}", user.getEmail());
        
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Nom d'utilisateur déjà pris: {}", user.getUsername());
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Email déjà utilisé: {}", user.getEmail());
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        if (!user.getPassword().equals(confirmPassword)) {
            logger.warn("Les mots de passe ne correspondent pas pour l'utilisateur: {}", user.getEmail());
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }
        if (user.getPassword().length() < 6) {
            logger.warn("Mot de passe trop court pour l'utilisateur: {}", user.getEmail());
            throw new RuntimeException("Le mot de passe doit contenir au moins 6 caractères");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            
            Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> {
                    logger.error("Role USER non trouvé");
                    return new RuntimeException("Role USER non trouvé");
                });
            user.setRoles(new HashSet<>());
            user.getRoles().add(userRole);
            
            User savedUser = userRepository.save(user);
            logger.info("Utilisateur enregistré avec succès: {}", savedUser.getEmail());
            return savedUser;
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement de l'utilisateur: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'inscription: " + e.getMessage());
        }
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Ce compte est désactivé");
        }

        return user;
    }

    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getCurrentUser(email);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Les nouveaux mots de passe ne correspondent pas");
        }

        if (newPassword.length() < 6) {
            throw new RuntimeException("Le mot de passe doit contenir au moins 6 caractères");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
} 