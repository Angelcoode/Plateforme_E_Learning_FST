package com.fst.elearning.service;

import com.fst.elearning.entity.Question;
import com.fst.elearning.entity.Quiz;
import com.fst.elearning.entity.QuizResult;
import com.fst.elearning.entity.ReponseApprenant;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.repository.QuizRepository;
import com.fst.elearning.repository.QuizResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;

    public QuizService(QuizRepository quizRepository, QuizResultRepository quizResultRepository) {
        this.quizRepository = quizRepository;
        this.quizResultRepository = quizResultRepository;
    }

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public Optional<Quiz> findByModuleId(Long moduleId) {
        return quizRepository.findByModuleId(moduleId);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Transactional
    public Quiz save(Quiz quiz) {
        if (quiz.getQuestions() != null) {
            quiz.getQuestions().forEach(q -> q.setQuiz(quiz));
        }
        return quizRepository.save(quiz);
    }

    @Transactional
    public void deleteById(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    @Transactional
    public QuizResult submitQuiz(Long quizId, Utilisateur utilisateur, Map<Long, String> reponses) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        int score = 0;
        List<ReponseApprenant> reponsesApprenant = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            String reponseChoisie = reponses.getOrDefault(question.getId(), "");
            boolean correcte = question.getCorrectAnswer().equalsIgnoreCase(reponseChoisie);
            if (correcte) score++;
            ReponseApprenant rep = new ReponseApprenant();
            rep.setQuestion(question);
            rep.setReponseChoisie(reponseChoisie);
            rep.setCorrecte(correcte);
            reponsesApprenant.add(rep);
        }
        QuizResult result = new QuizResult();
        result.setQuiz(quiz);
        result.setUtilisateur(utilisateur);
        result.setScore(score);
        result.setTotalQuestions(quiz.getQuestions().size());
        result.setDatePassage(LocalDateTime.now());
        QuizResult saved = quizResultRepository.save(result);
        reponsesApprenant.forEach(r -> r.setQuizResult(saved));
        saved.setReponses(reponsesApprenant);
        return quizResultRepository.save(saved);
    }

    public Optional<QuizResult> findLastResult(Long utilisateurId, Long quizId) {
        return quizResultRepository.findTopByUtilisateurIdAndQuizIdOrderByDatePassageDesc(utilisateurId, quizId);
    }

    public List<QuizResult> findResultsByUtilisateur(Long utilisateurId) {
        return quizResultRepository.findByUtilisateurId(utilisateurId);
    }
}
