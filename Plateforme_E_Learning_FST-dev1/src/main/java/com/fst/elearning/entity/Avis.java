package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private int note;

    @Column(length = 1000)
    private String commentaire;

    private LocalDateTime dateAvis;

    @PrePersist
    void prePersist() {
        if (dateAvis == null) {
            dateAvis = LocalDateTime.now();
        }
    }
}
