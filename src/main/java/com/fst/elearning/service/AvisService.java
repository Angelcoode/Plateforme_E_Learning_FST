package com.fst.elearning.service;

import com.fst.elearning.entity.Avis;
import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.repository.AvisRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvisService {

    private final AvisRepository avisRepository;

    public AvisService(AvisRepository avisRepository) {
        this.avisRepository = avisRepository;
    }

    public List<Avis> findByCoursId(Long coursId) {
        return avisRepository.findByCoursId(coursId);
    }

    public Optional<Avis> findByCoursIdAndUtilisateurId(Long coursId, Long utilisateurId) {
        return avisRepository.findByCoursIdAndUtilisateurId(coursId, utilisateurId);
    }

    public double getAverageNote(Long coursId) {
        Double avg = avisRepository.findAverageNoteByCoursId(coursId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    public Avis saveAvis(Long coursId, Long utilisateurId, int note, String commentaire,
                         CoursService coursService, UtilisateurService utilisateurService) {
        Avis avis = avisRepository.findByCoursIdAndUtilisateurId(coursId, utilisateurId)
                .orElse(new Avis());
        Cours cours = coursService.findById(coursId).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId).orElseThrow();
        avis.setCours(cours);
        avis.setUtilisateur(utilisateur);
        avis.setNote(note);
        avis.setCommentaire(commentaire);
        return avisRepository.save(avis);
    }

    public void deleteById(Long id) {
        avisRepository.deleteById(id);
    }
}
