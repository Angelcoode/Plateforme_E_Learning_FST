package com.fst.elearning.security;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur u = utilisateurRepository.findByEmail(username);
        if (u == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable: " + username);
        }

        String role = u.getRole() != null ? u.getRole().name() : "APPRENANT";
        return User.withUsername(u.getEmail())
                .password(u.getPassword() == null ? "" : u.getPassword())
                .roles(role) // -> ROLE_ADMIN, ROLE_FORMATEUR, ROLE_APPRENANT
                .build();
    }
}
