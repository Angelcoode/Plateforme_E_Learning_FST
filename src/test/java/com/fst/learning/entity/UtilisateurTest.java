package com.fst.learning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fst.learning.enums.Role;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
    }

    @Test
    void testUtilisateurCreation() {
        assertNotNull(utilisateur);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        utilisateur.setId(id);
        assertEquals(id, utilisateur.getId());
    }

    @Test
    void testSetAndGetNom() {
        String nom = "Ahmed Tounsi";
        utilisateur.setNom(nom);
        assertEquals(nom, utilisateur.getNom());
    }

    @Test
    void testSetAndGetEmail() {
        String email = "ahmed@example.com";
        utilisateur.setEmail(email);
        assertEquals(email, utilisateur.getEmail());
    }

    @Test
    void testSetAndGetPassword() {
        String password = "securePassword123";
        utilisateur.setPassword(password);
        assertEquals(password, utilisateur.getPassword());
    }

    @Test
    void testSetAndGetRole() {
        Role role = Role.APPRENANT;
        utilisateur.setRole(role);
        assertEquals(role, utilisateur.getRole());
    }

    @Test
    void testSetAndGetRoleFormateur() {
        Role role = Role.FORMATEUR;
        utilisateur.setRole(role);
        assertEquals(role, utilisateur.getRole());
    }

    @Test
    void testUtilisateurWithAllFields() {
        utilisateur.setId(1L);
        utilisateur.setNom("Fatima");
        utilisateur.setEmail("fatima@example.com");
        utilisateur.setPassword("pwd123");
        utilisateur.setRole(Role.APPRENANT);

        assertEquals(1L, utilisateur.getId());
        assertEquals("Fatima", utilisateur.getNom());
        assertEquals("fatima@example.com", utilisateur.getEmail());
        assertEquals("pwd123", utilisateur.getPassword());
        assertEquals(Role.APPRENANT, utilisateur.getRole());
    }

    @Test
    void testUtilisateurNullValues() {
        utilisateur.setNom(null);
        utilisateur.setEmail(null);
        utilisateur.setPassword(null);
        utilisateur.setRole(null);

        assertNull(utilisateur.getNom());
        assertNull(utilisateur.getEmail());
        assertNull(utilisateur.getPassword());
        assertNull(utilisateur.getRole());
    }
}
