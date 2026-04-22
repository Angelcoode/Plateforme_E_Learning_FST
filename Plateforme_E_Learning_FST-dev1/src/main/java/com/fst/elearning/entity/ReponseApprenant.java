package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReponseApprenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_result_id", nullable = false)
    private QuizResult quizResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private String reponseChoisie;

    private boolean correcte;
}
