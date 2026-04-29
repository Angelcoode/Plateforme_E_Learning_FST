package com.fst.learning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InscriptionTest {

    private Inscription inscription;
    private Utilisateur apprenant;
    private Cours cours;

    @BeforeEach
    void setUp() {
        inscription = new Inscription();
        apprenant = new Utilisateur();
        apprenant.setId(1L);
        apprenant.setNom("Etudiant 1");

        cours = new Cours();
        cours.setId(1L);
        cours.setTitre("Java Basics");
    }

    @Test
    void testInscriptionCreation() {
        assertNotNull(inscription);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        inscription.setId(id);
        assertEquals(id, inscription.getId());
    }

    @Test
    void testSetAndGetApprenant() {
        inscription.setApprenant(apprenant);
        assertEquals(apprenant, inscription.getApprenant());
    }

    @Test
    void testSetAndGetCours() {
        inscription.setCours(cours);
        assertEquals(cours, inscription.getCours());
    }

    @Test
    void testSetAndGetDateInscription() {
        LocalDate date = LocalDate.now();
        inscription.setDateInscription(date);
        assertEquals(date, inscription.getDateInscription());
    }

    @Test
    void testSetAndGetStatutEnAttente() {
        Inscription.StatutInscription statut = Inscription.StatutInscription.EN_ATTENTE;
        inscription.setStatut(statut);
        assertEquals(statut, inscription.getStatut());
    }

    @Test
    void testSetAndGetStatutAcceptee() {
        Inscription.StatutInscription statut = Inscription.StatutInscription.ACCEPTEE;
        inscription.setStatut(statut);
        assertEquals(statut, inscription.getStatut());
    }

    @Test
    void testSetAndGetStatutRefusee() {
        Inscription.StatutInscription statut = Inscription.StatutInscription.REFUSEE;
        inscription.setStatut(statut);
        assertEquals(statut, inscription.getStatut());
    }

    @Test
    void testSetAndGetStatutTerminee() {
        Inscription.StatutInscription statut = Inscription.StatutInscription.TERMINEE;
        inscription.setStatut(statut);
        assertEquals(statut, inscription.getStatut());
    }

    @Test
    void testInscriptionWithAllFields() {
        inscription.setId(1L);
        inscription.setApprenant(apprenant);
        inscription.setCours(cours);
        LocalDate dateInscription = LocalDate.now();
        inscription.setDateInscription(dateInscription);
        inscription.setStatut(Inscription.StatutInscription.ACCEPTEE);

        assertEquals(1L, inscription.getId());
        assertEquals(apprenant, inscription.getApprenant());
        assertEquals(cours, inscription.getCours());
        assertEquals(dateInscription, inscription.getDateInscription());
        assertEquals(Inscription.StatutInscription.ACCEPTEE, inscription.getStatut());
    }

    @Test
    void testInscriptionDefaultValues() {
        assertNull(inscription.getId());
        assertNull(inscription.getApprenant());
        assertNull(inscription.getCours());
        assertNull(inscription.getDateInscription());
        assertNull(inscription.getStatut());
    }

    @Test
    void testInscriptionStatutTransition() {
        inscription.setStatut(Inscription.StatutInscription.EN_ATTENTE);
        assertEquals(Inscription.StatutInscription.EN_ATTENTE, inscription.getStatut());

        inscription.setStatut(Inscription.StatutInscription.ACCEPTEE);
        assertEquals(Inscription.StatutInscription.ACCEPTEE, inscription.getStatut());

        inscription.setStatut(Inscription.StatutInscription.TERMINEE);
        assertEquals(Inscription.StatutInscription.TERMINEE, inscription.getStatut());
    }

    @Test
    void testInscriptionPastDate() {
        LocalDate pastDate = LocalDate.now().minusDays(30);
        inscription.setDateInscription(pastDate);
        assertEquals(pastDate, inscription.getDateInscription());
    }
}
