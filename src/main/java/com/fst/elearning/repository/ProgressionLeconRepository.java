package com.fst.elearning.repository;

import com.fst.elearning.entity.ProgressionLecon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressionLeconRepository extends JpaRepository<ProgressionLecon, Long> {
    
    Optional<ProgressionLecon> findByApprenantIdAndLeconId(Long apprenantId, Long leconId);

    @Query("SELECT COUNT(p) FROM ProgressionLecon p WHERE p.apprenant.id = :utilisateurId AND p.lecon.module.cours.id = :coursId AND p.completee = true")
    long countCompletedLeconsByUtilisateurAndCours(@Param("utilisateurId") Long utilisateurId, @Param("coursId") Long coursId);

    @Query("SELECT COUNT(l) FROM Lecon l WHERE l.module.cours.id = :coursId")
    long countTotalLeconsByCours(@Param("coursId") Long coursId);
}
