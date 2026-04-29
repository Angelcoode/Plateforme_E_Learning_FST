package com.fst.elearning.web;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import com.fst.elearning.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UtilisateurService utilisateurService;

    public RegisterController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Utilisateur utilisateur,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {
        // Validate email uniqueness
        if (utilisateurService.findByEmail(utilisateur.getEmail()) != null) {
            model.addAttribute("errorEmail", "Cet email est déjà utilisé.");
            return "auth/register";
        }
        // Validate password confirmation
        if (!utilisateur.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorPassword", "Les mots de passe ne correspondent pas.");
            return "auth/register";
        }
        // Default role is APPRENANT for self-registration
        utilisateur.setRole(Role.APPRENANT);
        utilisateurService.save(utilisateur);
        return "redirect:/login?registered";
    }
}
