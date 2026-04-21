package com.fst.learning.web;

import com.fst.learning.entity.ProgressionLesson;
import com.fst.learning.repository.LessonRepository;
import com.fst.learning.repository.ProgressionLessonRepository;
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
@RequestMapping("/ui/progressions")
public class ProgressionUiController {

    private final ProgressionLessonRepository progressionLessonRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final LessonRepository lessonRepository;

    public ProgressionUiController(
            ProgressionLessonRepository progressionLessonRepository,
            UtilisateurRepository utilisateurRepository,
            LessonRepository lessonRepository
    ) {
        this.progressionLessonRepository = progressionLessonRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("progressions", progressionLessonRepository.findAll());
        return "ui/progressions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        ProgressionLesson progression = new ProgressionLesson();
        progression.setCompletee(false);
        model.addAttribute("progression", progression);
        model.addAttribute("apprenants", utilisateurRepository.findAll());
        model.addAttribute("lessons", lessonRepository.findAll());
        return "ui/progressions/form";
    }

    @PostMapping
    public String create(
            @ModelAttribute ProgressionLesson progression,
            @RequestParam(required = false) Long apprenantId,
            @RequestParam(required = false) Long lessonId
    ) {
        if (apprenantId != null) {
            progression.setApprenant(utilisateurRepository.findById(apprenantId).orElse(null));
        } else {
            progression.setApprenant(null);
        }
        if (lessonId != null) {
            progression.setLesson(lessonRepository.findById(lessonId).orElse(null));
        } else {
            progression.setLesson(null);
        }
        if (progression.isCompletee() && progression.getDateCompletion() == null) {
            progression.setDateCompletion(LocalDateTime.now());
        }
        progressionLessonRepository.save(progression);
        return "redirect:/ui/progressions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ProgressionLesson progression = progressionLessonRepository.findById(id).orElseThrow();
        model.addAttribute("progression", progression);
        model.addAttribute("apprenants", utilisateurRepository.findAll());
        model.addAttribute("lessons", lessonRepository.findAll());
        return "ui/progressions/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute ProgressionLesson progression,
            @RequestParam(required = false) Long apprenantId,
            @RequestParam(required = false) Long lessonId
    ) {
        progression.setId(id);
        if (apprenantId != null) {
            progression.setApprenant(utilisateurRepository.findById(apprenantId).orElse(null));
        } else {
            progression.setApprenant(null);
        }
        if (lessonId != null) {
            progression.setLesson(lessonRepository.findById(lessonId).orElse(null));
        } else {
            progression.setLesson(null);
        }
        if (progression.isCompletee() && progression.getDateCompletion() == null) {
            progression.setDateCompletion(LocalDateTime.now());
        }
        if (!progression.isCompletee()) {
            progression.setDateCompletion(null);
        }
        progressionLessonRepository.save(progression);
        return "redirect:/ui/progressions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        progressionLessonRepository.deleteById(id);
        return "redirect:/ui/progressions";
    }
}

