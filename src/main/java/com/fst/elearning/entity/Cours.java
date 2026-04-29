package com.fst.elearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cours {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public BigDecimal getPrix() {
		return prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public Integer getDureeHeures() {
		return dureeHeures;
	}

	public void setDureeHeures(Integer dureeHeures) {
		this.dureeHeures = dureeHeures;
	}

	public Utilisateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Utilisateur formateur) {
		this.formateur = formateur;
	}

	public java.time.LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.time.LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public List<Paiement> getPaiements() {
		return paiements;
	}

	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}

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

    /** Prix en TND (0 = gratuit) */
    @Column(precision = 10, scale = 2)
    private BigDecimal prix;

    /** Durée estimée en heures */
    private Integer dureeHeures;

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

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Paiement> paiements = new ArrayList<>();

    @PrePersist
    void prePersist() {
        if (dateCreation == null) dateCreation = java.time.LocalDateTime.now();
        if (prix == null) prix = BigDecimal.ZERO;
    }

    public boolean isGratuit() {
        return prix == null || prix.compareTo(BigDecimal.ZERO) == 0;
    }

    public enum Niveau {
        DEBUTANT, INTERMEDIAIRE, AVANCE
    }
}
