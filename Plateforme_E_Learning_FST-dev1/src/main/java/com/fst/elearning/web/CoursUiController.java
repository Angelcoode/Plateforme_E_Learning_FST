package com.fst.elearning.web;

import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Module;
import com.fst.elearning.entity.Quiz;
import com.fst.elearning.entity.QuizResult;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.service.AvisService;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.InscriptionService;
import com.fst.elearning.service.ProgressionLeconService;
import com.fst.elearning.service.QuizService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ui/cours")
public class CoursUiController {

    private final CoursService coursService;
    private final UtilisateurService utilisateurService;
    private final InscriptionService inscriptionService;
    private final ProgressionLeconService progressionService;
    private final QuizService quizService;
    private final AvisService avisService;

    public CoursUiController(CoursService coursService,
                              UtilisateurService utilisateurService,
                              InscriptionService inscriptionService,
                              ProgressionLeconService progressionService,
                              QuizService quizService,
                              AvisService avisService) {
        this.coursService = coursService;
        this.utilisateurService = utilisateurService;
        this.inscriptionService = inscriptionService;
        this.progressionService = progressionService;
        this.quizService = quizService;
        this.avisService = avisService;
    }

    @GetMapping
    public String list(Model model, @PageableDefault(size = 6) Pageable pageable) {
        Page<Cours> page = coursService.findAll(pageable);
        model.addAttribute("cours", page.getContent());
        model.addAttribute("page", page);
        return "ui/cours/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id,
                          @AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        Cours cours = coursService.findById(id).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());

        List<Module> modules = coursService.findModulesByCours(id);
        boolean inscrit = coursService.isInscrit(utilisateur.getId(), id);
        double progression = inscrit ? progressionService.calculateProgression(utilisateur.getId(), id) : 0.0;
        double noteAvg = avisService.getAverageNote(id);
        var avis = avisService.findByCoursId(id);
        var monAvis = avisService.findByCoursIdAndUtilisateurId(id, utilisateur.getId()).orElse(null);

        model.addAttribute("cours", cours);
        model.addAttribute("modules", modules);
        model.addAttribute("inscrit", inscrit);
        model.addAttribute("progression", progression);
        model.addAttribute("noteAvg", noteAvg);
        model.addAttribute("avis", avis);
        model.addAttribute("monAvis", monAvis);
        model.addAttribute("utilisateur", utilisateur);

        modules.forEach(m -> {
            m.setLecons(coursService.findLeconsByModule(m.getId()));
            quizService.findByModuleId(m.getId()).ifPresent(q -> {
                quizService.findLastResult(utilisateur.getId(), q.getId())
                        .ifPresent(r -> model.addAttribute("quizResult_" + q.getId(), r));
            });
        });

        return "ui/cours/details";
    }

    @PostMapping("/{id}/inscrire")
    public String inscrire(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Cours cours = coursService.findById(id).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        inscriptionService.inscrire(utilisateur, cours);
        return "redirect:/ui/cours/" + id;
    }

    @PostMapping("/{id}/avis")
    public String posterAvis(@PathVariable Long id,
                             @RequestParam int note,
                             @RequestParam(required = false) String commentaire,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        avisService.saveAvis(id, utilisateur.getId(), note, commentaire, coursService, utilisateurService);
        return "redirect:/ui/cours/" + id + "#avis";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("niveaux", Cours.Niveau.values());
        model.addAttribute("formateurs", utilisateurService.findAll());
        return "ui/cours/form";
    }

    @PostMapping
    public String create(@ModelAttribute Cours cours,
                         @RequestParam(required = false) Long formateurId,
                         @RequestParam(value = "image", required = false) MultipartFile image) {
        if (formateurId != null) {
            cours.setFormateur(utilisateurService.findById(formateurId).orElse(null));
        }
        coursService.save(cours, image);
        return "redirect:/ui/cours";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Cours cours = coursService.findById(id).orElseThrow();
        model.addAttribute("cours", cours);
        model.addAttribute("niveaux", Cours.Niveau.values());
        model.addAttribute("formateurs", utilisateurService.findAll());
        return "ui/cours/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Cours cours,
                         @RequestParam(required = false) Long formateurId,
                         @RequestParam(value = "image", required = false) MultipartFile image) {
        cours.setId(id);
        if (formateurId != null) {
            cours.setFormateur(utilisateurService.findById(formateurId).orElse(null));
        }
        Cours existing = coursService.findById(id).orElse(cours);
        if (image == null || image.isEmpty()) {
            cours.setImageUrl(existing.getImageUrl());
            cours.setDateCreation(existing.getDateCreation());
            coursService.save(cours, null);
        } else {
            cours.setDateCreation(existing.getDateCreation());
            coursService.save(cours, image);
        }
        return "redirect:/ui/cours";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        coursService.deleteById(id);
        return "redirect:/ui/cours";
    }
}
