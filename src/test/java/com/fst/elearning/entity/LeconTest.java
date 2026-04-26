package com.fst.elearning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeconTest {

    private Lecon lecon;
    private Module module;

    @BeforeEach
    void setUp() {
        lecon = new Lecon();
        module = new Module();
        module.setId(1L);
        module.setTitre("Module 1");
    }

    @Test
    void testLeconCreation() {
        assertNotNull(lecon);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        lecon.setId(id);
        assertEquals(id, lecon.getId());
    }

    @Test
    void testSetAndGetTitre() {
        String titre = "Introduction to Classes";
        lecon.setTitre(titre);
        assertEquals(titre, lecon.getTitre());
    }

    @Test
    void testSetAndGetContenu() {
        String contenu = "Classes are blueprints for objects...";
        lecon.setContenu(contenu);
        assertEquals(contenu, lecon.getContenu());
    }

    @Test
    void testSetAndGetOrdre() {
        int ordre = 1;
        lecon.setOrdre(ordre);
        assertEquals(ordre, lecon.getOrdre());
    }

    @Test
    void testSetAndGetDureeMin() {
        int dureeMin = 45;
        lecon.setDureeMin(dureeMin);
        assertEquals(dureeMin, lecon.getDureeMin());
    }

    @Test
    void testSetAndGetModule() {
        lecon.setModule(module);
        assertEquals(module, lecon.getModule());
    }

    @Test
    void testLeconWithAllFields() {
        lecon.setId(1L);
        lecon.setTitre("Variables and Data Types");
        lecon.setContenu("Learn about different data types in Java");
        lecon.setOrdre(2);
        lecon.setDureeMin(60);
        lecon.setModule(module);

        assertEquals(1L, lecon.getId());
        assertEquals("Variables and Data Types", lecon.getTitre());
        assertEquals("Learn about different data types in Java", lecon.getContenu());
        assertEquals(2, lecon.getOrdre());
        assertEquals(60, lecon.getDureeMin());
        assertEquals(module, lecon.getModule());
    }

    @Test
    void testLeconDefaultValues() {
        assertNull(lecon.getId());
        assertNull(lecon.getTitre());
        assertNull(lecon.getContenu());
        assertNull(lecon.getModule());
        assertEquals(0, lecon.getOrdre());
        assertEquals(0, lecon.getDureeMin());
    }

    @Test
    void testLeconDureeMinPositive() {
        lecon.setDureeMin(120);
        assertEquals(120, lecon.getDureeMin());
    }

    @Test
    void testLeconDureeMinZero() {
        lecon.setDureeMin(0);
        assertEquals(0, lecon.getDureeMin());
    }

    @Test
    void testLeconWithLongContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("This is lecon content ");
        }
        lecon.setContenu(sb.toString());
        assertTrue(lecon.getContenu().length() > 1000);
    }
}
