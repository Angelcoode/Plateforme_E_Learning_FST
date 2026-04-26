package com.fst.elearning.controller;

import com.fst.elearning.entity.Lecon;
import com.fst.elearning.service.LeconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecons")
public class LeconController {

    private final LeconService leconService;

    public LeconController(LeconService leconService) {
        this.leconService = leconService;
    }

    @GetMapping
    public List<Lecon> getAllLecons() {
        return leconService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecon> getLeconById(@PathVariable Long id) {
        return leconService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Lecon createLecon(@RequestBody Lecon lecon) {
        return leconService.save(lecon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecon> updateLecon(@PathVariable Long id, @RequestBody Lecon leconDetails) {
        return leconService.findById(id)
                .map(lecon -> {
                    lecon.setTitre(leconDetails.getTitre());
                    lecon.setContenu(leconDetails.getContenu());
                    lecon.setOrdre(leconDetails.getOrdre());
                    lecon.setDureeMin(leconDetails.getDureeMin());
                    lecon.setModule(leconDetails.getModule());
                    return ResponseEntity.ok(leconService.save(lecon));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLecon(@PathVariable Long id) {
        return leconService.findById(id)
                .map(lecon -> {
                    leconService.deleteById(lecon.getId());
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
