package com.fst.learning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonTest {

    private Lesson lesson;
    private Module module;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        module = new Module();
        module.setId(1L);
        module.setTitre("Module 1");
    }

    @Test
    void testLessonCreation() {
        assertNotNull(lesson);
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        lesson.setId(id);
        assertEquals(id, lesson.getId());
    }

    @Test
    void testSetAndGetTitre() {
        String titre = "Introduction to Classes";
        lesson.setTitre(titre);
        assertEquals(titre, lesson.getTitre());
    }

    @Test
    void testSetAndGetContenu() {
        String contenu = "Classes are blueprints for objects...";
        lesson.setContenu(contenu);
        assertEquals(contenu, lesson.getContenu());
    }

    @Test
    void testSetAndGetOrdre() {
        int ordre = 1;
        lesson.setOrdre(ordre);
        assertEquals(ordre, lesson.getOrdre());
    }

    @Test
    void testSetAndGetDureeMin() {
        int dureeMin = 45;
        lesson.setDureeMin(dureeMin);
        assertEquals(dureeMin, lesson.getDureeMin());
    }

    @Test
    void testSetAndGetModule() {
        lesson.setModule(module);
        assertEquals(module, lesson.getModule());
    }

    @Test
    void testLessonWithAllFields() {
        lesson.setId(1L);
        lesson.setTitre("Variables and Data Types");
        lesson.setContenu("Learn about different data types in Java");
        lesson.setOrdre(2);
        lesson.setDureeMin(60);
        lesson.setModule(module);

        assertEquals(1L, lesson.getId());
        assertEquals("Variables and Data Types", lesson.getTitre());
        assertEquals("Learn about different data types in Java", lesson.getContenu());
        assertEquals(2, lesson.getOrdre());
        assertEquals(60, lesson.getDureeMin());
        assertEquals(module, lesson.getModule());
    }

    @Test
    void testLessonDefaultValues() {
        assertNull(lesson.getId());
        assertNull(lesson.getTitre());
        assertNull(lesson.getContenu());
        assertNull(lesson.getModule());
        assertEquals(0, lesson.getOrdre());
        assertEquals(0, lesson.getDureeMin());
    }

    @Test
    void testLessonDureeMinPositive() {
        lesson.setDureeMin(120);
        assertEquals(120, lesson.getDureeMin());
    }

    @Test
    void testLessonDureeMinZero() {
        lesson.setDureeMin(0);
        assertEquals(0, lesson.getDureeMin());
    }

    @Test
    void testLessonWithLongContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("This is lesson content ");
        }
        lesson.setContenu(sb.toString());
        assertTrue(lesson.getContenu().length() > 1000);
    }
}
