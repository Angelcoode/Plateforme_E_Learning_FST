package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 2000)
    private String description;

    private String categorie;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    private String imageUrl;
    private boolean actif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formateur_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Utilisateur formateur;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime dateCreation;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Module> modules = new ArrayList<>();

    @PrePersist
    void prePersist() {
        if (dateCreation == null) {
            dateCreation = java.time.LocalDateTime.now();
        }
    }

    public enum Niveau {
        DEBUTANT, INTERMEDIAIRE, AVANCE
    }
}
