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
    private Long formateurId;
}
