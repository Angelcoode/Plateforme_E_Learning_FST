package com.fst.elearning.repository;

import com.fst.elearning.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    List<Inscription> findByApprenantId(Long apprenantId);

    List<Inscription> findByCoursId(Long coursId);

    Optional<Inscription> findByApprenantIdAndCoursId(Long apprenantId, Long coursId);

    @Query("SELECT COUNT(i) FROM Inscription i WHERE i.cours.id = :coursId")
    long countByCoursId(@Param("coursId") Long coursId);
}
