package com.fst.elearning.repository;

import com.fst.elearning.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByUtilisateurId(Long utilisateurId);

    Optional<QuizResult> findTopByUtilisateurIdAndQuizIdOrderByDatePassageDesc(Long utilisateurId, Long quizId);
}
