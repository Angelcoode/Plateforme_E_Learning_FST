package com.fst.elearning.repository;

import com.fst.elearning.entity.Cours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    Page<Cours> findAll(Pageable pageable);

    Page<Cours> findByNiveau(Cours.Niveau niveau, Pageable pageable);

    @Query("SELECT c FROM Cours c WHERE c.prix IS NULL OR c.prix = 0")
    Page<Cours> findGratuits(Pageable pageable);

    @Query("SELECT c FROM Cours c WHERE c.niveau = :niveau AND (c.prix IS NULL OR c.prix = 0)")
    Page<Cours> findByNiveauAndGratuit(@Param("niveau") Cours.Niveau niveau, Pageable pageable);
}
