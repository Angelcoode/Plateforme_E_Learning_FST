package com.fst.elearning.config;

import com.fst.elearning.entity.Utilisateur;
import com.fst.elearning.enums.Role;
import com.fst.elearning.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedUsers(UtilisateurRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmail("admin@fst.ma") == null) {
                Utilisateur admin = new Utilisateur();
                admin.setNom("Administrateur");
                admin.setEmail("admin@fst.ma");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                repo.save(admin);
            }
            if (repo.findByEmail("formateur@fst.ma") == null) {
                Utilisateur formateur = new Utilisateur();
                formateur.setNom("Formateur Principal");
                formateur.setEmail("formateur@fst.ma");
                formateur.setPassword(encoder.encode("formateur123"));
                formateur.setRole(Role.FORMATEUR);
                repo.save(formateur);
            }
            if (repo.findByEmail("apprenant@fst.ma") == null) {
                Utilisateur apprenant = new Utilisateur();
                apprenant.setNom("Apprenant Test");
                apprenant.setEmail("apprenant@fst.ma");
                apprenant.setPassword(encoder.encode("apprenant123"));
                apprenant.setRole(Role.APPRENANT);
                repo.save(apprenant);
            }
        };
    }
}
