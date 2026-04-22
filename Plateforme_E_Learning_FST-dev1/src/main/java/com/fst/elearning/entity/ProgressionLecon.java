package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
public class ProgressionLecon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Utilisateur apprenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecon_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Lecon lecon;

    private boolean completee;
    private LocalDateTime dateCompletion;
}
