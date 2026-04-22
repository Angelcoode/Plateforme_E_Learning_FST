package com.fst.elearning.web;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.InscriptionService;
import com.fst.elearning.service.ProgressionLeconService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UtilisateurService utilisateurService;
    private final CoursService coursService;
    private final InscriptionService inscriptionService;
    private final ProgressionLeconService progressionService;

    public HomeController(UtilisateurService utilisateurService,
                          CoursService coursService,
                          InscriptionService inscriptionService,
                          ProgressionLeconService progressionService) {
        this.utilisateurService = utilisateurService;
        this.coursService = coursService;
        this.inscriptionService = inscriptionService;
        this.progressionService = progressionService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        if (utilisateur == null) {
            return "redirect:/login";
        }

        model.addAttribute("utilisateur", utilisateur);

        if (utilisateur.getRole() == Role.ADMIN) {
            model.addAttribute("totalCours", coursService.countAll());
            model.addAttribute("totalApprenants", utilisateurService.countByRole(Role.APPRENANT));
            model.addAttribute("totalFormateurs", utilisateurService.countByRole(Role.FORMATEUR));
            model.addAttribute("totalUtilisateurs", utilisateurService.countAll());
            return "ui/dashboard";
        }

        if (utilisateur.getRole() == Role.FORMATEUR) {
            model.addAttribute("totalCours", coursService.countAll());
            return "ui/dashboard";
        }

        // APPRENANT
        model.addAttribute("inscriptions", inscriptionService.findByApprenant(utilisateur.getId()));
        model.addAttribute("cours", coursService.findAll());
        return "ui/dashboard";
    }
}
