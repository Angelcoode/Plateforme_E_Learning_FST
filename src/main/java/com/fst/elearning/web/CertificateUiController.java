package com.fst.elearning.web;

import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.ProgressionLeconService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/ui/certificat")
public class CertificateUiController {

    private final ProgressionLeconService progressionService;
    private final UtilisateurService utilisateurService;
    private final CoursService coursService;

    public CertificateUiController(ProgressionLeconService progressionService,
                                    UtilisateurService utilisateurService,
                                    CoursService coursService) {
        this.progressionService = progressionService;
        this.utilisateurService = utilisateurService;
        this.coursService = coursService;
    }

    @GetMapping("/{utilisateurId}/{coursId}")
    public String generateCertificate(@PathVariable Long utilisateurId,
                                      @PathVariable Long coursId,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      Model model) {
        double progression = progressionService.calculateProgression(utilisateurId, coursId);

        if (progression < 100.0) {
            return "redirect:/ui/cours/" + coursId + "?error=not-completed";
        }

        Utilisateur u = utilisateurService.findById(utilisateurId).orElseThrow();
        Cours c = coursService.findById(coursId).orElseThrow();

        model.addAttribute("nom", u.getNom());
        model.addAttribute("cours", c.getTitre());
        model.addAttribute("date", LocalDate.now());
        return "ui/certificat/view";
    }

    @PostMapping("/mark-complete/{apprenantId}/{leconId}")
    public String markLecon(@PathVariable Long apprenantId,
                            @PathVariable Long leconId,
                            @org.springframework.web.bind.annotation.RequestParam Long coursId) {
        progressionService.markAsCompleted(apprenantId, leconId);
        return "redirect:/ui/cours/" + coursId;
    }
}
