package com.fst.elearning.web;

import com.fst.elearning.entity.Question;
import com.fst.elearning.entity.Quiz;
import com.fst.elearning.entity.QuizResult;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.service.ModuleService;
import com.fst.elearning.service.QuizService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/quiz")
public class QuizUiController {

    private final QuizService quizService;
    private final UtilisateurService utilisateurService;
    private final ModuleService moduleService;

    public QuizUiController(QuizService quizService,
                             UtilisateurService utilisateurService,
                             ModuleService moduleService) {
        this.quizService = quizService;
        this.utilisateurService = utilisateurService;
        this.moduleService = moduleService;
    }

    // ─── PASSER UN QUIZ (Apprenant) ──────────────────────────────────────────

    @GetMapping("/{quizId}")
    public String showQuiz(@PathVariable Long quizId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        quizService.findLastResult(utilisateur.getId(), quizId)
                   .ifPresent(r -> model.addAttribute("previousResult", r));
        model.addAttribute("quiz", quiz);
        model.addAttribute("utilisateur", utilisateur);
        return "ui/quiz/take";
    }

    @PostMapping("/{quizId}/submit")
    public String submitQuiz(@PathVariable Long quizId,
                             @RequestParam Map<String, String> params,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        Map<Long, String> reponses = new HashMap<>();
        params.forEach((key, value) -> {
            if (key.startsWith("q_")) {
                try {
                    reponses.put(Long.parseLong(key.substring(2)), value);
                } catch (NumberFormatException ignored) {}
            }
        });
        QuizResult result = quizService.submitQuiz(quizId, utilisateur, reponses);
        return "redirect:/ui/quiz/" + quizId + "/result/" + result.getId();
    }

    @GetMapping("/{quizId}/result/{resultId}")
    public String showResult(@PathVariable Long quizId,
                             @PathVariable Long resultId,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());
        QuizResult result = quizService.findLastResult(utilisateur.getId(), quizId).orElseThrow();
        int pct = result.getTotalQuestions() > 0
                ? (int) Math.round((double) result.getScore() / result.getTotalQuestions() * 100)
                : 0;
        model.addAttribute("quiz", quiz);
        model.addAttribute("result", result);
        model.addAttribute("pct", pct);
        return "ui/quiz/result";
    }

    // ─── CRÉER UN QUIZ (Formateur / Admin) ───────────────────────────────────

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("modules", moduleService.findAll());
        return "ui/quiz/form";
    }

    @PostMapping("/create")
    public String create(@RequestParam String titre,
                         @RequestParam Long moduleId,
                         @RequestParam Map<String, String> params,
                         RedirectAttributes redirectAttributes) {
        try {
            Quiz quiz = new Quiz();
            quiz.setTitre(titre);
            moduleService.findById(moduleId).ifPresent(quiz::setModule);
            List<Question> questions = parseQuestions(params, quiz);
            if (questions.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "Le quiz doit contenir au moins une question.");
                return "redirect:/ui/quiz/create";
            }
            quiz.setQuestions(questions);
            quizService.save(quiz);
            redirectAttributes.addFlashAttribute("successMsg",
                    "Quiz \"" + titre + "\" créé avec succès !");
            return "redirect:/ui/cours";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    "Erreur lors de la création : " + e.getMessage());
            return "redirect:/ui/quiz/create";
        }
    }

    // ─── MODIFIER UN QUIZ (Formateur / Admin) ────────────────────────────────

    @GetMapping("/{quizId}/edit")
    public String editForm(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        model.addAttribute("quiz", quiz);
        model.addAttribute("modules", moduleService.findAll());
        return "ui/quiz/form";
    }

    @PostMapping("/{quizId}/edit")
    public String update(@PathVariable Long quizId,
                         @RequestParam String titre,
                         @RequestParam Long moduleId,
                         @RequestParam Map<String, String> params,
                         RedirectAttributes redirectAttributes) {
        try {
            Quiz quiz = quizService.findById(quizId).orElseThrow();
            quiz.setTitre(titre);
            moduleService.findById(moduleId).ifPresent(quiz::setModule);
            List<Question> questions = parseQuestions(params, quiz);
            if (questions.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "Le quiz doit contenir au moins une question.");
                return "redirect:/ui/quiz/" + quizId + "/edit";
            }
            quiz.getQuestions().clear();
            quiz.getQuestions().addAll(questions);
            quizService.save(quiz);
            redirectAttributes.addFlashAttribute("successMsg", "Quiz mis à jour avec succès !");
            return "redirect:/ui/cours";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    "Erreur lors de la mise à jour : " + e.getMessage());
            return "redirect:/ui/quiz/" + quizId + "/edit";
        }
    }

    @PostMapping("/{quizId}/delete")
    public String delete(@PathVariable Long quizId, RedirectAttributes redirectAttributes) {
        quizService.deleteById(quizId);
        redirectAttributes.addFlashAttribute("successMsg", "Quiz supprimé.");
        return "redirect:/ui/cours";
    }

    // ─── Utilitaire ──────────────────────────────────────────────────────────

    private List<Question> parseQuestions(Map<String, String> params, Quiz quiz) {
        Map<Integer, Question> questionMap = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.isBlank() || !key.startsWith("questions[")) continue;
            try {
                int start = key.indexOf('[') + 1;
                int end = key.indexOf(']');
                int idx = Integer.parseInt(key.substring(start, end));
                String field = key.substring(end + 2);
                Question q = questionMap.computeIfAbsent(idx, i -> {
                    Question nq = new Question();
                    nq.setQuiz(quiz);
                    return nq;
                });
                switch (field) {
                    case "text"          -> q.setText(value);
                    case "optionA"       -> q.setOptionA(value);
                    case "optionB"       -> q.setOptionB(value);
                    case "optionC"       -> q.setOptionC(value);
                    case "optionD"       -> q.setOptionD(value);
                    case "correctAnswer" -> q.setCorrectAnswer(value);
                }
            } catch (NumberFormatException ignored) {}
        }
        List<Question> result = new ArrayList<>();
        for (Question q : questionMap.values()) {
            if (q.getText() != null && !q.getText().isBlank()
                    && q.getOptionA() != null && q.getOptionB() != null
                    && q.getOptionC() != null && q.getOptionD() != null
                    && q.getCorrectAnswer() != null) {
                result.add(q);
            }
        }
        return result;
    }
}
