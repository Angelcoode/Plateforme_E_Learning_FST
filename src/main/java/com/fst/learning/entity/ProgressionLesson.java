package com.fst.learning.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ProgressionLesson {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Utilisateur apprenant;

    @ManyToOne
    private Lesson lesson;

    private boolean completee;
    private LocalDateTime dateCompletion;
}
