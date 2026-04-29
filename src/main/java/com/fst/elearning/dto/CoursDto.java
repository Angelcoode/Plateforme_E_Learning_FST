package com.fst.elearning.dto;

import lombok.Data;

@Data
public class CoursDto {
    private Long id;
    private String titre;
    private String description;
    private String categorie;
    private String niveau;
    private String imageUrl;
    private boolean actif;
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
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
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
	public Long getFormateurId() {
		return formateurId;
	}
	public void setFormateurId(Long formateurId) {
		this.formateurId = formateurId;
	}
	private Long formateurId;
}
