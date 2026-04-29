package com.fst.learning.web;

import com.fst.learning.entity.Cours;
import com.fst.learning.repository.CoursRepository;
import com.fst.learning.repository.UtilisateurRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/ui/cours")
public class CoursUiController {

    private final CoursRepository coursRepository;
    private final UtilisateurRepository utilisateurRepository;

    public CoursUiController(CoursRepository coursRepository, UtilisateurRepository utilisateurRepository) {
        this.coursRepository = coursRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cours", coursRepository.findAll());
        return "ui/cours/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("niveaux", Cours.Niveau.values());
        model.addAttribute("formateurs", utilisateurRepository.findAll());
        return "ui/cours/form";
    }

    @PostMapping
    public String create(@ModelAttribute Cours cours, @RequestParam(required = false) Long formateurId) {
        if (cours.getDateCreation() == null) {
            cours.setDateCreation(LocalDateTime.now());
        }
        if (formateurId != null) {
            cours.setFormateur(utilisateurRepository.findById(formateurId).orElse(null));
        } else {
            cours.setFormateur(null);
        }
        coursRepository.save(cours);
        return "redirect:/ui/cours";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Cours cours = coursRepository.findById(id).orElseThrow();
        model.addAttribute("cours", cours);
        model.addAttribute("niveaux", Cours.Niveau.values());
        model.addAttribute("formateurs", utilisateurRepository.findAll());
        return "ui/cours/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Cours cours, @RequestParam(required = false) Long formateurId) {
        cours.setId(id);
        if (cours.getDateCreation() == null) {
            cours.setDateCreation(LocalDateTime.now());
        }
        if (formateurId != null) {
            cours.setFormateur(utilisateurRepository.findById(formateurId).orElse(null));
        } else {
            cours.setFormateur(null);
        }
        coursRepository.save(cours);
        return "redirect:/ui/cours";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        coursRepository.deleteById(id);
        return "redirect:/ui/cours";
    }
}

