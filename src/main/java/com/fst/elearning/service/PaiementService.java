package com.fst.elearning.service;

import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Paiement;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.repository.PaiementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final InscriptionService inscriptionService;

    public PaiementService(PaiementRepository paiementRepository,
                           InscriptionService inscriptionService) {
        this.paiementRepository = paiementRepository;
        this.inscriptionService = inscriptionService;
    }

    public Page<Paiement> findAll(Pageable pageable) {
        return paiementRepository.findAll(pageable);
    }

    public Page<Paiement> findByStatut(Paiement.StatutPaiement statut, Pageable pageable) {
        return paiementRepository.findByStatut(statut, pageable);
    }

    public List<Paiement> findByApprenant(Long apprenantId) {
        return paiementRepository.findByApprenantId(apprenantId);
    }

    public Optional<Paiement> findById(Long id) {
        return paiementRepository.findById(id);
    }

    public Optional<Paiement> findByApprenantAndCours(Long apprenantId, Long coursId) {
        return paiementRepository.findByApprenantIdAndCoursId(apprenantId, coursId);
    }

    /** Checks if an apprenant has a VALIDE paiement for a cours */
    public boolean hasPaiementValide(Long apprenantId, Long coursId) {
        return paiementRepository.findByApprenantIdAndCoursId(apprenantId, coursId)
                .map(p -> p.getStatut() == Paiement.StatutPaiement.VALIDE)
                .orElse(false);
    }

    /** Create a pending paiement request */
    @Transactional
    public Paiement initierPaiement(Utilisateur apprenant, Cours cours,
                                    Paiement.MethodePaiement methode) {
        // Avoid duplicate pending
        Optional<Paiement> existing = paiementRepository
                .findByApprenantIdAndCoursId(apprenant.getId(), cours.getId());
        if (existing.isPresent() && existing.get().getStatut() != Paiement.StatutPaiement.REFUSE
                && existing.get().getStatut() != Paiement.StatutPaiement.REMBOURSE) {
            return existing.get();
        }
        Paiement p = new Paiement();
        p.setApprenant(apprenant);
        p.setCours(cours);
        p.setMontant(cours.getPrix() != null ? cours.getPrix() : BigDecimal.ZERO);
        p.setMethode(methode);
        p.setStatut(Paiement.StatutPaiement.EN_ATTENTE);
        p.setReference("FST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return paiementRepository.save(p);
    }

    /** Admin validates a paiement and auto-inscribes the apprenant */
    @Transactional
    public Paiement valider(Long paiementId) {
        Paiement p = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));
        p.setStatut(Paiement.StatutPaiement.VALIDE);
        p.setDateValidation(LocalDateTime.now());
        paiementRepository.save(p);
        // Auto-inscrire l'apprenant dans le cours
        inscriptionService.inscrire(p.getApprenant(), p.getCours());
        return p;
    }

    /** Admin refuses a paiement */
    @Transactional
    public Paiement refuser(Long paiementId, String notes) {
        Paiement p = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));
        p.setStatut(Paiement.StatutPaiement.REFUSE);
        p.setNotes(notes);
        return paiementRepository.save(p);
    }

    @Transactional
    public Paiement rembourser(Long paiementId, String notes) {
        Paiement p = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));
        p.setStatut(Paiement.StatutPaiement.REMBOURSE);
        p.setNotes(notes);
        return paiementRepository.save(p);
    }

    // Stats
    public BigDecimal totalRevenuValide() {
        BigDecimal total = paiementRepository.sumMontantValide();
        return total != null ? total : BigDecimal.ZERO;
    }

    public long countByStatut(Paiement.StatutPaiement statut) {
        return paiementRepository.countByStatut(statut);
    }
}
