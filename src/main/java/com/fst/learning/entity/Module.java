package com.fst.learning.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Module {
    @Id @GeneratedValue
    private Long id;

    private String titre;
    private int ordre;

    @ManyToOne
    private Cours cours;
}