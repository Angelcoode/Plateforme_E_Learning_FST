package com.fst.learning.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Inscription {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Utilisateur apprenant;

    @ManyToOne
    private Cours cours;

    private LocalDate dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutInscription statut;

    public enum StatutInscription {
        EN_ATTENTE, ACCEPTEE, REFUSEE, TERMINEE
    }
}