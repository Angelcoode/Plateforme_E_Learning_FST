package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReponseApprenant {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuizResult getQuizResult() {
		return quizResult;
	}

	public void setQuizResult(QuizResult quizResult) {
		this.quizResult = quizResult;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getReponseChoisie() {
		return reponseChoisie;
	}

	public void setReponseChoisie(String reponseChoisie) {
		this.reponseChoisie = reponseChoisie;
	}

	public boolean isCorrecte() {
		return correcte;
	}

	public void setCorrecte(boolean correcte) {
		this.correcte = correcte;
	}

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
