package com.fst.learning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProgressionLessonTest {

    private ProgressionLesson progression;
    private Utilisateur apprenant;
    private Lesson lesson;

    @BeforeEach
    void setUp() {
        progression = new ProgressionLesson();
        apprenant = new Utilisateur();
        apprenant.setId(1L);
        apprenant.setNom("Apprenant Test");

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitre("Lesson 1");
    }

    @Test
    void testProgressionLessonCreation() {
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
    void testSetAndGetLesson() {
        progression.setLesson(lesson);
        assertEquals(lesson, progression.getLesson());
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
    void testProgressionLessonWithAllFields() {
        progression.setId(1L);
        progression.setApprenant(apprenant);
        progression.setLesson(lesson);
        progression.setCompletee(true);
        LocalDateTime dateCompletion = LocalDateTime.now();
        progression.setDateCompletion(dateCompletion);

        assertEquals(1L, progression.getId());
        assertEquals(apprenant, progression.getApprenant());
        assertEquals(lesson, progression.getLesson());
        assertTrue(progression.isCompletee());
        assertEquals(dateCompletion, progression.getDateCompletion());
    }

    @Test
    void testProgressionLessonDefaultValues() {
        assertNull(progression.getId());
        assertNull(progression.getApprenant());
        assertNull(progression.getLesson());
        assertFalse(progression.isCompletee());
        assertNull(progression.getDateCompletion());
    }

    @Test
    void testProgressionLessonIncomplete() {
        progression.setCompletee(false);
        progression.setDateCompletion(null);

        assertFalse(progression.isCompletee());
        assertNull(progression.getDateCompletion());
    }

    @Test
    void testProgressionLessonCompletedWithDate() {
        LocalDateTime completionDate = LocalDateTime.now();
        progression.setCompletee(true);
        progression.setDateCompletion(completionDate);

        assertTrue(progression.isCompletee());
        assertEquals(completionDate, progression.getDateCompletion());
    }

    @Test
    void testProgressionLessonCompletedWithoutDate() {
        progression.setCompletee(true);
        progression.setDateCompletion(null);

        assertTrue(progression.isCompletee());
        assertNull(progression.getDateCompletion());
    }

    @Test
    void testProgressionLessonNotCompletedWithDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        progression.setCompletee(false);
        progression.setDateCompletion(futureDate);

        assertFalse(progression.isCompletee());
        assertEquals(futureDate, progression.getDateCompletion());
    }
}
