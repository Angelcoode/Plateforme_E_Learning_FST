package com.fst.elearning.web;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/utilisateurs")
public class UtilisateurUiController {

    private final UtilisateurService utilisateurService;

    public UtilisateurUiController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        return "ui/utilisateurs/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", Role.values());
        return "ui/utilisateurs/form";
    }

    @PostMapping
    public String create(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.save(utilisateur);
        return "redirect:/ui/utilisateurs";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findById(id).orElseThrow();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("roles", Role.values());
        return "ui/utilisateurs/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        utilisateur.setId(id);
        utilisateurService.save(utilisateur);
        return "redirect:/ui/utilisateurs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        utilisateurService.deleteById(id);
        return "redirect:/ui/utilisateurs";
    }
}

