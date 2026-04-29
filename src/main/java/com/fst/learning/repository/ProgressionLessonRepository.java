package com.fst.learning.repository;

import com.fst.learning.entity.ProgressionLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressionLessonRepository extends JpaRepository<ProgressionLesson, Long> {
}
