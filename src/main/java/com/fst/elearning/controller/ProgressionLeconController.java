package com.fst.elearning.controller;

import com.fst.elearning.entity.ProgressionLecon;
import com.fst.elearning.service.ProgressionLeconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progressions")
public class ProgressionLeconController {

    private final ProgressionLeconService progressionLeconService;

    public ProgressionLeconController(ProgressionLeconService progressionLeconService) {
        this.progressionLeconService = progressionLeconService;
    }

    @GetMapping
    public List<ProgressionLecon> getAllProgressions() {
        return progressionLeconService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressionLecon> getProgressionById(@PathVariable Long id) {
        return progressionLeconService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProgressionLecon createProgression(@RequestBody ProgressionLecon progression) {
        return progressionLeconService.save(progression);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressionLecon> updateProgression(@PathVariable Long id, @RequestBody ProgressionLecon progressionDetails) {
        return progressionLeconService.findById(id)
                .map(progression -> {
                    progression.setApprenant(progressionDetails.getApprenant());
                    progression.setLecon(progressionDetails.getLecon());
                    progression.setCompletee(progressionDetails.isCompletee());
                    progression.setDateCompletion(progressionDetails.getDateCompletion());
                    return ResponseEntity.ok(progressionLeconService.save(progression));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgression(@PathVariable Long id) {
        return progressionLeconService.findById(id)
                .map(progression -> {
                    progressionLeconService.deleteById(progression.getId());
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
