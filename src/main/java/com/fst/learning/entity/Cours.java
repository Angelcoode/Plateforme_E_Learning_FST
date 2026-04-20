package com.fst.learning.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Cours {
	@Id
	@GeneratedValue
	private Long id;

	private String titre;
	@Column(length = 2000)
	private String description;

	private String categorie;

	@Enumerated(EnumType.STRING)
	private Niveau niveau;

	private String imageUrl;
	private boolean actif;

	@ManyToOne
	private Utilisateur formateur;

	private LocalDateTime dateCreation;

	public enum Niveau {
		DEBUTANT, INTERMEDIAIRE, AVANCE
	}
}
