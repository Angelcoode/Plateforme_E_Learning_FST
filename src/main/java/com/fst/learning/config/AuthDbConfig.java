package com.fst.learning.config;

import com.fst.learning.entity.Utilisateur;
import com.fst.learning.enums.Role;
import com.fst.learning.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthDbConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UtilisateurRepository utilisateurRepository) {
        return username -> {
            Utilisateur u = utilisateurRepository.findByEmail(username);
            if (u == null) {
                throw new UsernameNotFoundException("Utilisateur introuvable: " + username);
            }

            String role = u.getRole() != null ? u.getRole().name() : "APPRENANT";
            UserDetails details = User.withUsername(u.getEmail())
                    .password(u.getPassword() == null ? "" : u.getPassword())
                    .roles(role) // -> ROLE_ADMIN, ROLE_FORMATEUR, ROLE_APPRENANT
                    .build();
            return details;
        };
    }

    @Bean
    public CommandLineRunner seedAdmin(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@fst.ma";
            if (utilisateurRepository.findByEmail(adminEmail) != null) {
                return;
            }

            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setEmail(adminEmail);
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("admin123"));
            utilisateurRepository.save(admin);
        };
    }
}

