package com.fst.learning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    private Module module;
    private Cours cours;

    @BeforeEach
    void setUp() {
        module = new Module();
        cours = new Cours();
        cours.setId(1L);
        cours.setTitre("Java Advanced");
    }

    @Test
    void testModuleCreation() {
        assertNotNull(module);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        module.setId(id);
        assertEquals(id, module.getId());
    }

    @Test
    void testSetAndGetTitre() {
        String titre = "OOP Concepts";
        module.setTitre(titre);
        assertEquals(titre, module.getTitre());
    }

    @Test
    void testSetAndGetOrdre() {
        int ordre = 2;
        module.setOrdre(ordre);
        assertEquals(ordre, module.getOrdre());
    }

    @Test
    void testSetAndGetCours() {
        module.setCours(cours);
        assertEquals(cours, module.getCours());
    }

    @Test
    void testModuleWithAllFields() {
        module.setId(1L);
        module.setTitre("Functions and Methods");
        module.setOrdre(3);
        module.setCours(cours);

        assertEquals(1L, module.getId());
        assertEquals("Functions and Methods", module.getTitre());
        assertEquals(3, module.getOrdre());
        assertEquals(cours, module.getCours());
    }

    @Test
    void testModuleDefaultValues() {
        assertNull(module.getId());
        assertNull(module.getTitre());
        assertNull(module.getCours());
        assertEquals(0, module.getOrdre());
    }

    @Test
    void testModuleOrdreZero() {
        module.setOrdre(0);
        assertEquals(0, module.getOrdre());
    }

    @Test
    void testModuleOrdreNegative() {
        module.setOrdre(-1);
        assertEquals(-1, module.getOrdre());
    }

    @Test
    void testModuleOrdrePositive() {
        module.setOrdre(100);
        assertEquals(100, module.getOrdre());
    }
}
