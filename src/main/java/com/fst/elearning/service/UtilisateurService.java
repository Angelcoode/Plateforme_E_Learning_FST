package com.fst.elearning.service;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import com.fst.elearning.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                               PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public long countAll() {
        return utilisateurRepository.count();
    }

    public long countByRole(Role role) {
        return utilisateurRepository.countByRole(role);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateur.getPassword() != null && !utilisateur.getPassword().isEmpty()) {
            if (!utilisateur.getPassword().startsWith("$2a$")) {
                utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            }
        } else if (utilisateur.getId() != null) {
            utilisateurRepository.findById(utilisateur.getId()).ifPresent(existing ->
                    utilisateur.setPassword(existing.getPassword())
            );
        }
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
