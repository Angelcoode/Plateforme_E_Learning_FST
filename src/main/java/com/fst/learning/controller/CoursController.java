package com.fst.learning.controller;

import com.fst.learning.entity.Cours;
import com.fst.learning.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        return coursRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cours createCours(@RequestBody Cours cours) {
        return coursRepository.save(cours);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id, @RequestBody Cours coursDetails) {
        return coursRepository.findById(id)
                .map(cours -> {
                    cours.setTitre(coursDetails.getTitre());
                    cours.setDescription(coursDetails.getDescription());
                    cours.setCategorie(coursDetails.getCategorie());
                    cours.setNiveau(coursDetails.getNiveau());
                    cours.setImageUrl(coursDetails.getImageUrl());
                    cours.setActif(coursDetails.isActif());
                    cours.setFormateur(coursDetails.getFormateur());
                    cours.setDateCreation(coursDetails.getDateCreation());
                    return ResponseEntity.ok(coursRepository.save(cours));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCours(@PathVariable Long id) {
        return coursRepository.findById(id)
                .map(cours -> {
                    coursRepository.delete(cours);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
