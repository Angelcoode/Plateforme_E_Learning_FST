package com.fst.elearning.web;

import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Paiement;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.PaiementService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/paiements")
public class PaiementUiController {

    private final PaiementService paiementService;
    private final CoursService coursService;
    private final UtilisateurService utilisateurService;

    public PaiementUiController(PaiementService paiementService,
                                 CoursService coursService,
                                 UtilisateurService utilisateurService) {
        this.paiementService = paiementService;
        this.coursService = coursService;
        this.utilisateurService = utilisateurService;
    }

    /** Admin – list all paiements */
    @GetMapping
    public String list(@RequestParam(required = false) String statut,
                       @PageableDefault(size = 10) Pageable pageable,
                       Model model) {
        Page<Paiement> page;
        if (statut != null && !statut.isBlank()) {
            try {
                Paiement.StatutPaiement s = Paiement.StatutPaiement.valueOf(statut);
                page = paiementService.findByStatut(s, pageable);
                model.addAttribute("filtreStatut", statut);
            } catch (IllegalArgumentException e) {
                page = paiementService.findAll(pageable);
            }
        } else {
            page = paiementService.findAll(pageable);
        }
        model.addAttribute("paiements", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("statuts", Paiement.StatutPaiement.values());
        model.addAttribute("totalRevenu", paiementService.totalRevenuValide());
        model.addAttribute("nbEnAttente", paiementService.countByStatut(Paiement.StatutPaiement.EN_ATTENTE));
        model.addAttribute("nbValide", paiementService.countByStatut(Paiement.StatutPaiement.VALIDE));
        return "ui/paiements/list";
    }

    /** Apprenant – show payment form for a cours */
    @GetMapping("/cours/{coursId}")
    public String payerForm(@PathVariable Long coursId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Cours cours = coursService.findById(coursId).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());

        // Already has a valid paiement?
        paiementService.findByApprenantAndCours(utilisateur.getId(), coursId)
                .ifPresent(p -> model.addAttribute("paiementExistant", p));

        model.addAttribute("cours", cours);
        model.addAttribute("methodes", Paiement.MethodePaiement.values());
        return "ui/paiements/form";
    }

    /** Apprenant – submit paiement */
    @PostMapping("/cours/{coursId}")
    public String payer(@PathVariable Long coursId,
                        @RequestParam String methode,
                        @AuthenticationPrincipal UserDetails userDetails,
                        RedirectAttributes redirectAttributes) {
        Cours cours = coursService.findById(coursId).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        Paiement.MethodePaiement m = Paiement.MethodePaiement.valueOf(methode);
        Paiement p = paiementService.initierPaiement(utilisateur, cours, m);
        redirectAttributes.addFlashAttribute("successMsg",
                "Demande de paiement envoyée ! Référence : " + p.getReference());
        return "redirect:/ui/cours/" + coursId;
    }

    /** Admin – validate */
    @PostMapping("/{id}/valider")
    public String valider(@PathVariable Long id, RedirectAttributes ra) {
        paiementService.valider(id);
        ra.addFlashAttribute("successMsg", "Paiement validé et apprenant inscrit.");
        return "redirect:/ui/paiements";
    }

    /** Admin – refuse */
    @PostMapping("/{id}/refuser")
    public String refuser(@PathVariable Long id,
                          @RequestParam(required = false) String notes,
                          RedirectAttributes ra) {
        paiementService.refuser(id, notes);
        ra.addFlashAttribute("errorMsg", "Paiement refusé.");
        return "redirect:/ui/paiements";
    }

    /** Admin – rembourser */
    @PostMapping("/{id}/rembourser")
    public String rembourser(@PathVariable Long id,
                             @RequestParam(required = false) String notes,
                             RedirectAttributes ra) {
        paiementService.rembourser(id, notes);
        ra.addFlashAttribute("successMsg", "Remboursement enregistré.");
        return "redirect:/ui/paiements";
    }

    /** Apprenant – my paiements */
    @GetMapping("/mes-paiements")
    public String mesPaiements(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        model.addAttribute("paiements", paiementService.findByApprenant(utilisateur.getId()));
        return "ui/paiements/mes-paiements";
    }
}
