package com.fst.elearning.service;

import com.fst.elearning.entity.Lecon;
import com.fst.elearning.entity.ProgressionLecon;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.repository.LeconRepository;
import com.fst.elearning.repository.ProgressionLeconRepository;
import com.fst.elearning.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressionLeconService {

    private final ProgressionLeconRepository progressionLeconRepository;
    private final LeconRepository leconRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ProgressionLeconService(ProgressionLeconRepository progressionLeconRepository,
                                   LeconRepository leconRepository,
                                   UtilisateurRepository utilisateurRepository) {
        this.progressionLeconRepository = progressionLeconRepository;
        this.leconRepository = leconRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<ProgressionLecon> findAll() {
        return progressionLeconRepository.findAll();
    }

    public Optional<ProgressionLecon> findById(Long id) {
        return progressionLeconRepository.findById(id);
    }

    public ProgressionLecon save(ProgressionLecon progressionLecon) {
        return progressionLeconRepository.save(progressionLecon);
    }

    public void deleteById(Long id) {
        progressionLeconRepository.deleteById(id);
    }

    public ProgressionLecon markAsCompleted(Long apprenantId, Long leconId) {
        ProgressionLecon progression = progressionLeconRepository
                .findByApprenantIdAndLeconId(apprenantId, leconId)
                .orElse(new ProgressionLecon());

        if (progression.getId() == null) {
            Utilisateur apprenant = utilisateurRepository.findById(apprenantId)
                    .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable: " + apprenantId));
            Lecon lecon = leconRepository.findById(leconId)
                    .orElseThrow(() -> new IllegalArgumentException("Leçon introuvable: " + leconId));
            progression.setApprenant(apprenant);
            progression.setLecon(lecon);
        }

        if (!progression.isCompletee()) {
            progression.setCompletee(true);
            progression.setDateCompletion(LocalDateTime.now());
        }
        return progressionLeconRepository.save(progression);
    }

    public double calculateProgression(Long apprenantId, Long coursId) {
        long totalLecons = progressionLeconRepository.countTotalLeconsByCours(coursId);
        if (totalLecons == 0) return 0.0;
        long completedLecons = progressionLeconRepository
                .countCompletedLeconsByUtilisateurAndCours(apprenantId, coursId);
        return Math.round(((double) completedLecons / totalLecons) * 100.0);
    }
}
