package com.fst.elearning.web;

import com.fst.elearning.entity.Inscription;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.InscriptionService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/ui/inscriptions")
public class InscriptionUiController {

    private final InscriptionService inscriptionService;
    private final UtilisateurService utilisateurService;
    private final CoursService coursService;

    public InscriptionUiController(
            InscriptionService inscriptionService,
            UtilisateurService utilisateurService,
            CoursService coursService
    ) {
        this.inscriptionService = inscriptionService;
        this.utilisateurService = utilisateurService;
        this.coursService = coursService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("inscriptions", inscriptionService.findAll());
        return "ui/inscriptions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Inscription inscription = new Inscription();
        inscription.setDateInscription(LocalDate.now());
        model.addAttribute("inscription", inscription);
        model.addAttribute("statuts", Inscription.StatutInscription.values());
        model.addAttribute("apprenants", utilisateurService.findAll());
        model.addAttribute("cours", coursService.findAll());
        return "ui/inscriptions/form";
    }

    @PostMapping
    public String create(
            @ModelAttribute Inscription inscription,
            @RequestParam(required = false) Long apprenantId,
            @RequestParam(required = false) Long coursId
    ) {
        if (inscription.getDateInscription() == null) {
            inscription.setDateInscription(LocalDate.now());
        }
        if (apprenantId != null) {
            inscription.setApprenant(utilisateurService.findById(apprenantId).orElse(null));
        } else {
            inscription.setApprenant(null);
        }
        if (coursId != null) {
            inscription.setCours(coursService.findById(coursId).orElse(null));
        } else {
            inscription.setCours(null);
        }
        inscriptionService.save(inscription);
        return "redirect:/ui/inscriptions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Inscription inscription = inscriptionService.findById(id).orElseThrow();
        model.addAttribute("inscription", inscription);
        model.addAttribute("statuts", Inscription.StatutInscription.values());
        model.addAttribute("apprenants", utilisateurService.findAll());
        model.addAttribute("cours", coursService.findAll());
        return "ui/inscriptions/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute Inscription inscription,
            @RequestParam(required = false) Long apprenantId,
            @RequestParam(required = false) Long coursId
    ) {
        inscription.setId(id);
        if (inscription.getDateInscription() == null) {
            inscription.setDateInscription(LocalDate.now());
        }
        if (apprenantId != null) {
            inscription.setApprenant(utilisateurService.findById(apprenantId).orElse(null));
        } else {
            inscription.setApprenant(null);
        }
        if (coursId != null) {
            inscription.setCours(coursService.findById(coursId).orElse(null));
        } else {
            inscription.setCours(null);
        }
        inscriptionService.save(inscription);
        return "redirect:/ui/inscriptions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        inscriptionService.deleteById(id);
        return "redirect:/ui/inscriptions";
    }
}

