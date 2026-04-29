package com.fst.elearning.repository;

import com.fst.elearning.entity.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByApprenantId(Long apprenantId);

    List<Paiement> findByCoursId(Long coursId);

    Optional<Paiement> findByApprenantIdAndCoursId(Long apprenantId, Long coursId);

    Page<Paiement> findByStatut(Paiement.StatutPaiement statut, Pageable pageable);

    Page<Paiement> findAll(Pageable pageable);

    @Query("SELECT COALESCE(SUM(p.montant),0) FROM Paiement p WHERE p.statut = 'VALIDE'")
    BigDecimal sumMontantValide();

    @Query("SELECT COUNT(p) FROM Paiement p WHERE p.statut = :statut")
    long countByStatut(@Param("statut") Paiement.StatutPaiement statut);
}
