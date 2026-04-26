package com.fst.elearning.web;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.InscriptionService;
import com.fst.elearning.service.PaiementService;
import com.fst.elearning.service.ProgressionLeconService;
import com.fst.elearning.service.UtilisateurService;
import com.fst.elearning.entity.Paiement;
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
    private final PaiementService paiementService;

    public HomeController(UtilisateurService utilisateurService,
                          CoursService coursService,
                          InscriptionService inscriptionService,
                          ProgressionLeconService progressionService,
                          PaiementService paiementService) {
        this.utilisateurService = utilisateurService;
        this.coursService = coursService;
        this.inscriptionService = inscriptionService;
        this.progressionService = progressionService;
        this.paiementService = paiementService;
    }

    /** Public landing page */
    @GetMapping({"/", "/home"})
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) return "redirect:/dashboard";
        model.addAttribute("totalCours", coursService.countAll());
        model.addAttribute("totalApprenants", utilisateurService.countByRole(Role.APPRENANT));
        model.addAttribute("totalFormateurs", utilisateurService.countByRole(Role.FORMATEUR));
        model.addAttribute("coursList", coursService.findLatest(6));
        return "home";
    }

    /** Authenticated dashboard */
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        if (utilisateur == null) return "redirect:/login";

        model.addAttribute("utilisateur", utilisateur);

        if (utilisateur.getRole() == Role.ADMIN) {
            model.addAttribute("totalCours", coursService.countAll());
            model.addAttribute("totalApprenants", utilisateurService.countByRole(Role.APPRENANT));
            model.addAttribute("totalFormateurs", utilisateurService.countByRole(Role.FORMATEUR));
            model.addAttribute("totalUtilisateurs", utilisateurService.countAll());
            model.addAttribute("totalRevenu", paiementService.totalRevenuValide());
            model.addAttribute("nbPaiementsEnAttente",
                    paiementService.countByStatut(Paiement.StatutPaiement.EN_ATTENTE));
            return "ui/dashboard";
        }
        if (utilisateur.getRole() == Role.FORMATEUR) {
            model.addAttribute("totalCours", coursService.countAll());
            return "ui/dashboard";
        }
        // APPRENANT
        model.addAttribute("inscriptions", inscriptionService.findByApprenant(utilisateur.getId()));
        model.addAttribute("mesPaiements", paiementService.findByApprenant(utilisateur.getId()));
        model.addAttribute("cours", coursService.findAll());
        return "ui/dashboard";
    }
}
