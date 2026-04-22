package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Utilisateur apprenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cours cours;

    private LocalDate dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutInscription statut;

    @PrePersist
    void prePersist() {
        if (dateInscription == null) {
            dateInscription = LocalDate.now();
        }
        if (statut == null) {
            statut = StatutInscription.EN_ATTENTE;
        }
    }

    public enum StatutInscription {
        EN_ATTENTE, ACCEPTEE, REFUSEE, TERMINEE
    }
}