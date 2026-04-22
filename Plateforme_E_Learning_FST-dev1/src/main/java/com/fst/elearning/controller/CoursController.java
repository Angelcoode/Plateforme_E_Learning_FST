package com.fst.elearning.controller;

import com.fst.elearning.entity.Cours;
import com.fst.elearning.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @GetMapping
    public List<Cours> getAllCours() {
        return coursService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        return coursService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cours createCours(@RequestBody Cours cours) {
        return coursService.save(cours, null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id, @RequestBody Cours coursDetails) {
        return coursService.findById(id)
                .map(cours -> {
                    cours.setTitre(coursDetails.getTitre());
                    cours.setDescription(coursDetails.getDescription());
                    cours.setCategorie(coursDetails.getCategorie());
                    cours.setNiveau(coursDetails.getNiveau());
                    cours.setImageUrl(coursDetails.getImageUrl());
                    cours.setActif(coursDetails.isActif());
                    cours.setFormateur(coursDetails.getFormateur());
                    cours.setDateCreation(coursDetails.getDateCreation());
                    return ResponseEntity.ok(coursService.save(cours, null));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCours(@PathVariable Long id) {
        return coursService.findById(id)
                .map(cours -> {
                    coursService.deleteById(cours.getId());
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
