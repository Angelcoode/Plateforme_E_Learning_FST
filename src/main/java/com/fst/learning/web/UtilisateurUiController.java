package com.fst.learning.web;

import com.fst.learning.entity.Utilisateur;
import com.fst.learning.enums.Role;
import com.fst.learning.repository.UtilisateurRepository;
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

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurUiController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("utilisateurs", utilisateurRepository.findAll());
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
        utilisateurRepository.save(utilisateur);
        return "redirect:/ui/utilisateurs";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("roles", Role.values());
        return "ui/utilisateurs/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        utilisateur.setId(id);
        utilisateurRepository.save(utilisateur);
        return "redirect:/ui/utilisateurs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        utilisateurRepository.deleteById(id);
        return "redirect:/ui/utilisateurs";
    }
}

