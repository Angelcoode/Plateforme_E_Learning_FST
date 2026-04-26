package com.fst.elearning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProgressionLeconTest {

    private ProgressionLecon progression;
    private Utilisateur apprenant;
    private Lecon lecon;

    @BeforeEach
    void setUp() {
        progression = new ProgressionLecon();
        apprenant = new Utilisateur();
        apprenant.setId(1L);
        apprenant.setNom("Apprenant Test");

        lecon = new Lecon();
        lecon.setId(1L);
        lecon.setTitre("Lecon 1");
    }

    @Test
    void testProgressionLeconCreation() {
        assertNotNull(progression);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        progression.setId(id);
        assertEquals(id, progression.getId());
    }

    @Test
    void testSetAndGetApprenant() {
        progression.setApprenant(apprenant);
        assertEquals(apprenant, progression.getApprenant());
    }

    @Test
    void testSetAndGetLecon() {
        progression.setLecon(lecon);
        assertEquals(lecon, progression.getLecon());
    }

    @Test
    void testSetAndGetCompl() {
        progression.setCompletee(true);
        assertTrue(progression.isCompletee());

        progression.setCompletee(false);
        assertFalse(progression.isCompletee());
    }

    @Test
    void testSetAndGetDateCompletion() {
        LocalDateTime now = LocalDateTime.now();
        progression.setDateCompletion(now);
        assertEquals(now, progression.getDateCompletion());
    }

    @Test
    void testProgressionLeconWithAllFields() {
        progression.setId(1L);
        progression.setApprenant(apprenant);
        progression.setLecon(lecon);
        progression.setCompletee(true);
        LocalDateTime dateCompletion = LocalDateTime.now();
        progression.setDateCompletion(dateCompletion);

        assertEquals(1L, progression.getId());
        assertEquals(apprenant, progression.getApprenant());
        assertEquals(lecon, progression.getLecon());
        assertTrue(progression.isCompletee());
        assertEquals(dateCompletion, progression.getDateCompletion());
    }

    @Test
    void testProgressionLeconDefaultValues() {
        assertNull(progression.getId());
        assertNull(progression.getApprenant());
        assertNull(progression.getLecon());
        assertFalse(progression.isCompletee());
        assertNull(progression.getDateCompletion());
    }

    @Test
    void testProgressionLeconIncomplete() {
        progression.setCompletee(false);
        progression.setDateCompletion(null);

        assertFalse(progression.isCompletee());
        assertNull(progression.getDateCompletion());
    }

    @Test
    void testProgressionLeconCompletedWithDate() {
        LocalDateTime completionDate = LocalDateTime.now();
        progression.setCompletee(true);
        progression.setDateCompletion(completionDate);

        assertTrue(progression.isCompletee());
        assertEquals(completionDate, progression.getDateCompletion());
    }

    @Test
    void testProgressionLeconCompletedWithoutDate() {
        progression.setCompletee(true);
        progression.setDateCompletion(null);

        assertTrue(progression.isCompletee());
        assertNull(progression.getDateCompletion());
    }

    @Test
    void testProgressionLeconNotCompletedWithDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        progression.setCompletee(false);
        progression.setDateCompletion(futureDate);

        assertFalse(progression.isCompletee());
        assertEquals(futureDate, progression.getDateCompletion());
    }
}
