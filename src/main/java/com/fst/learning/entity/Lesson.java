package com.fst.learning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Lesson {
    @Id @GeneratedValue
    private Long id;

    private String titre;

    @Column(length = 3000)
    private String contenu;

    private int ordre;
    private int dureeMin;

    @ManyToOne
    private Module module;
}
