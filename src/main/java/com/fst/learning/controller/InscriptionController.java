package com.fst.learning.controller;

import com.fst.learning.entity.Inscription;
import com.fst.learning.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @GetMapping
    public List<Inscription> getAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscription> getInscriptionById(@PathVariable Long id) {
        return inscriptionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inscription createInscription(@RequestBody Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscription> updateInscription(@PathVariable Long id, @RequestBody Inscription inscriptionDetails) {
        return inscriptionRepository.findById(id)
                .map(inscription -> {
                    inscription.setApprenant(inscriptionDetails.getApprenant());
                    inscription.setCours(inscriptionDetails.getCours());
                    inscription.setDateInscription(inscriptionDetails.getDateInscription());
                    inscription.setStatut(inscriptionDetails.getStatut());
                    return ResponseEntity.ok(inscriptionRepository.save(inscription));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInscription(@PathVariable Long id) {
        return inscriptionRepository.findById(id)
                .map(inscription -> {
                    inscriptionRepository.delete(inscription);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
