package com.fst.elearning.service;

import com.fst.elearning.entity.Inscription;
import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.repository.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    public List<Inscription> findAll() {
        return inscriptionRepository.findAll();
    }

    public Optional<Inscription> findById(Long id) {
        return inscriptionRepository.findById(id);
    }

    public List<Inscription> findByApprenant(Long apprenantId) {
        return inscriptionRepository.findByApprenantId(apprenantId);
    }

    public List<Inscription> findByCours(Long coursId) {
        return inscriptionRepository.findByCoursId(coursId);
    }

    public Inscription inscrire(Utilisateur apprenant, Cours cours) {
        return inscriptionRepository.findByApprenantIdAndCoursId(apprenant.getId(), cours.getId())
                .orElseGet(() -> {
                    Inscription inscription = new Inscription();
                    inscription.setApprenant(apprenant);
                    inscription.setCours(cours);
                    inscription.setDateInscription(LocalDate.now());
                    inscription.setStatut(Inscription.StatutInscription.EN_ATTENTE);
                    return inscriptionRepository.save(inscription);
                });
    }

    public Inscription save(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    public void deleteById(Long id) {
        inscriptionRepository.deleteById(id);
    }
}
