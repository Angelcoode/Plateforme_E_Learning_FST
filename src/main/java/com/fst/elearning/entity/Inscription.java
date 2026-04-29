package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
public class Inscription {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utilisateur getApprenant() {
		return apprenant;
	}

	public void setApprenant(Utilisateur apprenant) {
		this.apprenant = apprenant;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	public StatutInscription getStatut() {
		return statut;
	}

	public void setStatut(StatutInscription statut) {
		this.statut = statut;
	}

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