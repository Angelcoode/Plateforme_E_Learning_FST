package com.fst.learning.controller;

import com.fst.learning.entity.ProgressionLesson;
import com.fst.learning.repository.ProgressionLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progressions")
public class ProgressionLessonController {

    @Autowired
    private ProgressionLessonRepository progressionLessonRepository;

    @GetMapping
    public List<ProgressionLesson> getAllProgressions() {
        return progressionLessonRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressionLesson> getProgressionById(@PathVariable Long id) {
        return progressionLessonRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProgressionLesson createProgression(@RequestBody ProgressionLesson progression) {
        return progressionLessonRepository.save(progression);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressionLesson> updateProgression(@PathVariable Long id, @RequestBody ProgressionLesson progressionDetails) {
        return progressionLessonRepository.findById(id)
                .map(progression -> {
                    progression.setApprenant(progressionDetails.getApprenant());
                    progression.setLesson(progressionDetails.getLesson());
                    progression.setCompletee(progressionDetails.isCompletee());
                    progression.setDateCompletion(progressionDetails.getDateCompletion());
                    return ResponseEntity.ok(progressionLessonRepository.save(progression));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgression(@PathVariable Long id) {
        return progressionLessonRepository.findById(id)
                .map(progression -> {
                    progressionLessonRepository.delete(progression);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
