package com.fst.elearning.web;

import com.fst.elearning.entity.Quiz;
import com.fst.elearning.entity.QuizResult;
import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.service.QuizService;
import com.fst.elearning.service.UtilisateurService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ui/quiz")
public class QuizUiController {

    private final QuizService quizService;
    private final UtilisateurService utilisateurService;

    public QuizUiController(QuizService quizService, UtilisateurService utilisateurService) {
        this.quizService = quizService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/{quizId}")
    public String showQuiz(@PathVariable Long quizId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());

        quizService.findLastResult(utilisateur.getId(), quizId).ifPresent(r ->
                model.addAttribute("previousResult", r)
        );

        model.addAttribute("quiz", quiz);
        model.addAttribute("utilisateur", utilisateur);
        return "ui/quiz/take";
    }

    @PostMapping("/{quizId}/submit")
    public String submitQuiz(@PathVariable Long quizId,
                             @RequestParam Map<String, String> params,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        Utilisateur utilisateur = utilisateurService.findByEmail(userDetails.getUsername());

        Map<Long, String> reponses = new HashMap<>();
        params.forEach((key, value) -> {
            if (key.startsWith("q_")) {
                try {
                    Long questionId = Long.parseLong(key.substring(2));
                    reponses.put(questionId, value);
                } catch (NumberFormatException ignored) {
                }
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
}
