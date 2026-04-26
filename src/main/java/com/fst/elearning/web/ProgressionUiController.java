package com.fst.elearning.web;

import com.fst.elearning.entity.ProgressionLecon;
import com.fst.elearning.service.LeconService;
import com.fst.elearning.service.ProgressionLeconService;
import com.fst.elearning.service.UtilisateurService;
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
@RequestMapping("/ui/progressions")
public class ProgressionUiController {

    private final ProgressionLeconService progressionLeconService;
    private final UtilisateurService utilisateurService;
    private final LeconService leconService;

    public ProgressionUiController(
            ProgressionLeconService progressionLeconService,
            UtilisateurService utilisateurService,
            LeconService leconService
    ) {
        this.progressionLeconService = progressionLeconService;
        this.utilisateurService = utilisateurService;
        this.leconService = leconService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("progressions", progressionLeconService.findAll());
        return "ui/progressions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        ProgressionLecon progression = new ProgressionLecon();
        progression.setCompletee(false);
        model.addAttribute("progression", progression);
        model.addAttribute("apprenants", utilisateurService.findAll());
        model.addAttribute("lecons", leconService.findAll());
        return "ui/progressions/form";
    }

    @PostMapping
    public String create(
            @ModelAttribute ProgressionLecon progression,
            @RequestParam(required = false) Long apprenantId,
            @RequestParam(required = false) Long leconId
    ) {
        if (apprenantId != null) {
            progression.setApprenant(utilisateurService.findById(apprenantId).orElse(null));
        } else {
            progression.setApprenant(null);
        }
        if (leconId != null) {
            progression.setLecon(leconService.findById(leconId).orElse(null));
        } else {
            progression.setLecon(null);
        }
        if (progression.isCompletee() && progression.getDateCompletion() == null) {
            progression.setDateCompletion(LocalDateTime.now());
        }
        progressionLeconService.save(progression);
        return "redirect:/ui/progressions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ProgressionLecon progression = progressionLeconService.findById(id).orElseThrow();
        model.addAttribute("progression", progression);
        model.addAttribute("apprenants", utilisateurService.findAll());
        model.addAttribute("lecons", leconService.findAll());
        return "ui/progressions/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute ProgressionLecon progression,
            @RequestParam(required = false) Long apprenantId,
            @RequestParam(required = false) Long leconId
    ) {
        progression.setId(id);
        if (apprenantId != null) {
            progression.setApprenant(utilisateurService.findById(apprenantId).orElse(null));
        } else {
            progression.setApprenant(null);
        }
        if (leconId != null) {
            progression.setLecon(leconService.findById(leconId).orElse(null));
        } else {
            progression.setLecon(null);
        }
        if (progression.isCompletee() && progression.getDateCompletion() == null) {
            progression.setDateCompletion(LocalDateTime.now());
        }
        if (!progression.isCompletee()) {
            progression.setDateCompletion(null);
        }
        progressionLeconService.save(progression);
        return "redirect:/ui/progressions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        progressionLeconService.deleteById(id);
        return "redirect:/ui/progressions";
    }
}

