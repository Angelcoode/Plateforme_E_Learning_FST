package com.fst.learning.web;

import com.fst.learning.entity.Inscription;
import com.fst.learning.repository.CoursRepository;
import com.fst.learning.repository.InscriptionRepository;
import com.fst.learning.repository.UtilisateurRepository;
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

    private final InscriptionRepository inscriptionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CoursRepository coursRepository;

    public InscriptionUiController(
            InscriptionRepository inscriptionRepository,
            UtilisateurRepository utilisateurRepository,
            CoursRepository coursRepository
    ) {
        this.inscriptionRepository = inscriptionRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.coursRepository = coursRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("inscriptions", inscriptionRepository.findAll());
        return "ui/inscriptions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Inscription inscription = new Inscription();
        inscription.setDateInscription(LocalDate.now());
        model.addAttribute("inscription", inscription);
        model.addAttribute("statuts", Inscription.StatutInscription.values());
        model.addAttribute("apprenants", utilisateurRepository.findAll());
        model.addAttribute("cours", coursRepository.findAll());
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
            inscription.setApprenant(utilisateurRepository.findById(apprenantId).orElse(null));
        } else {
            inscription.setApprenant(null);
        }
        if (coursId != null) {
            inscription.setCours(coursRepository.findById(coursId).orElse(null));
        } else {
            inscription.setCours(null);
        }
        inscriptionRepository.save(inscription);
        return "redirect:/ui/inscriptions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Inscription inscription = inscriptionRepository.findById(id).orElseThrow();
        model.addAttribute("inscription", inscription);
        model.addAttribute("statuts", Inscription.StatutInscription.values());
        model.addAttribute("apprenants", utilisateurRepository.findAll());
        model.addAttribute("cours", coursRepository.findAll());
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
            inscription.setApprenant(utilisateurRepository.findById(apprenantId).orElse(null));
        } else {
            inscription.setApprenant(null);
        }
        if (coursId != null) {
            inscription.setCours(coursRepository.findById(coursId).orElse(null));
        } else {
            inscription.setCours(null);
        }
        inscriptionRepository.save(inscription);
        return "redirect:/ui/inscriptions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        inscriptionRepository.deleteById(id);
        return "redirect:/ui/inscriptions";
    }
}

