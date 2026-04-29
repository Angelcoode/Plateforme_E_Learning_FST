package com.fst.elearning.repository;

import com.fst.elearning.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {

    List<Avis> findByCoursId(Long coursId);

    Optional<Avis> findByCoursIdAndUtilisateurId(Long coursId, Long utilisateurId);

    @Query("SELECT AVG(a.note) FROM Avis a WHERE a.cours.id = :coursId")
    Double findAverageNoteByCoursId(@Param("coursId") Long coursId);
}
