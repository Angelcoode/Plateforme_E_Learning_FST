package com.fst.elearning.repository;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);

    long countByRole(Role role);
}
