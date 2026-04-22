package com.fst.elearning.controller;

import com.fst.elearning.entity.Inscription;
import com.fst.elearning.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @GetMapping
    public List<Inscription> getAllInscriptions() {
        return inscriptionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscription> getInscriptionById(@PathVariable Long id) {
        return inscriptionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inscription createInscription(@RequestBody Inscription inscription) {
        return inscriptionService.save(inscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscription> updateInscription(@PathVariable Long id, @RequestBody Inscription inscriptionDetails) {
        return inscriptionService.findById(id)
                .map(inscription -> {
                    inscription.setApprenant(inscriptionDetails.getApprenant());
                    inscription.setCours(inscriptionDetails.getCours());
                    inscription.setDateInscription(inscriptionDetails.getDateInscription());
                    inscription.setStatut(inscriptionDetails.getStatut());
                    return ResponseEntity.ok(inscriptionService.save(inscription));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInscription(@PathVariable Long id) {
        return inscriptionService.findById(id)
                .map(inscription -> {
                    inscriptionService.deleteById(inscription.getId());
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
