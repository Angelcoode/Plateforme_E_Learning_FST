package com.fst.elearning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CoursTest {

    private Cours cours;
    private Utilisateur formateur;

    @BeforeEach
    void setUp() {
        cours = new Cours();
        formateur = new Utilisateur();
        formateur.setId(1L);
        formateur.setNom("Mr. Bennasser");
    }

    @Test
    void testCoursCreation() {
        assertNotNull(cours);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        cours.setId(id);
        assertEquals(id, cours.getId());
    }

    @Test
    void testSetAndGetTitre() {
        String titre = "Java Programming Basics";
        cours.setTitre(titre);
        assertEquals(titre, cours.getTitre());
    }

    @Test
    void testSetAndGetDescription() {
        String description = "Learn Java fundamentals from scratch";
        cours.setDescription(description);
        assertEquals(description, cours.getDescription());
    }

    @Test
    void testSetAndGetCategorie() {
        String categorie = "Programming";
        cours.setCategorie(categorie);
        assertEquals(categorie, cours.getCategorie());
    }

    @Test
    void testSetAndGetNiveau() {
        Cours.Niveau niveau = Cours.Niveau.DEBUTANT;
        cours.setNiveau(niveau);
        assertEquals(niveau, cours.getNiveau());
    }

    @Test
    void testSetAndGetNiveauIntermediate() {
        Cours.Niveau niveau = Cours.Niveau.INTERMEDIAIRE;
        cours.setNiveau(niveau);
        assertEquals(niveau, cours.getNiveau());
    }

    @Test
    void testSetAndGetNiveauAdvanced() {
        Cours.Niveau niveau = Cours.Niveau.AVANCE;
        cours.setNiveau(niveau);
        assertEquals(niveau, cours.getNiveau());
    }

    @Test
    void testSetAndGetImageUrl() {
        String imageUrl = "https://example.com/image.jpg";
        cours.setImageUrl(imageUrl);
        assertEquals(imageUrl, cours.getImageUrl());
    }

    @Test
    void testSetAndGetActif() {
        cours.setActif(true);
        assertTrue(cours.isActif());

        cours.setActif(false);
        assertFalse(cours.isActif());
    }

    @Test
    void testSetAndGetFormateur() {
        cours.setFormateur(formateur);
        assertEquals(formateur, cours.getFormateur());
    }

    @Test
    void testSetAndGetDateCreation() {
        LocalDateTime now = LocalDateTime.now();
        cours.setDateCreation(now);
        assertEquals(now, cours.getDateCreation());
    }

    @Test
    void testCoursWithAllFields() {
        cours.setId(1L);
        cours.setTitre("Spring Boot Course");
        cours.setDescription("Complete Spring Boot guide");
        cours.setCategorie("Backend");
        cours.setNiveau(Cours.Niveau.INTERMEDIAIRE);
        cours.setImageUrl("https://example.com/spring.jpg");
        cours.setActif(true);
        cours.setFormateur(formateur);
        LocalDateTime dateCreation = LocalDateTime.now();
        cours.setDateCreation(dateCreation);

        assertEquals(1L, cours.getId());
        assertEquals("Spring Boot Course", cours.getTitre());
        assertEquals("Complete Spring Boot guide", cours.getDescription());
        assertEquals("Backend", cours.getCategorie());
        assertEquals(Cours.Niveau.INTERMEDIAIRE, cours.getNiveau());
        assertEquals("https://example.com/spring.jpg", cours.getImageUrl());
        assertTrue(cours.isActif());
        assertEquals(formateur, cours.getFormateur());
        assertEquals(dateCreation, cours.getDateCreation());
    }

    @Test
    void testCoursDefaultValues() {
        assertNull(cours.getId());
        assertNull(cours.getTitre());
        assertNull(cours.getDescription());
        assertNull(cours.getCategorie());
        assertNull(cours.getNiveau());
        assertNull(cours.getImageUrl());
        assertFalse(cours.isActif());
    }
}
