package com.fst.elearning.service;

import com.fst.elearning.entity.Cours;
import com.fst.elearning.entity.Cours.Niveau;
import com.fst.elearning.entity.Lecon;
import com.fst.elearning.entity.Module;
import com.fst.elearning.repository.CoursRepository;
import com.fst.elearning.repository.InscriptionRepository;
import com.fst.elearning.repository.LeconRepository;
import com.fst.elearning.repository.ModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CoursService {

    private final CoursRepository coursRepository;
    private final ModuleRepository moduleRepository;
    private final LeconRepository leconRepository;
    private final InscriptionRepository inscriptionRepository;

    public CoursService(CoursRepository coursRepository,
                        ModuleRepository moduleRepository,
                        LeconRepository leconRepository,
                        InscriptionRepository inscriptionRepository) {
        this.coursRepository = coursRepository;
        this.moduleRepository = moduleRepository;
        this.leconRepository = leconRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    public Page<Cours> findAll(Pageable pageable) {
        return coursRepository.findAll(pageable);
    }

    public Page<Cours> findByNiveau(Niveau niveau, Pageable pageable) {
        return coursRepository.findByNiveau(niveau, pageable);
    }

    public Page<Cours> findGratuits(Pageable pageable) {
        return coursRepository.findGratuits(pageable);
    }

    public Page<Cours> findByNiveauAndGratuit(Niveau niveau, Pageable pageable) {
        return coursRepository.findByNiveauAndGratuit(niveau, pageable);
    }

    public List<Cours> findLatest(int count) {
        return coursRepository.findAll(
            PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "dateCreation"))
        ).getContent();
    }

    public Optional<Cours> findById(Long id) {
        return coursRepository.findById(id);
    }

    public long countAll() {
        return coursRepository.count();
    }

    public Cours save(Cours cours, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                Path uploadDir = Paths.get("uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Files.copy(image.getInputStream(), uploadDir.resolve(filename));
                cours.setImageUrl("/uploads/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'upload de l'image.", e);
            }
        }
        return coursRepository.save(cours);
    }

    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }

    public List<Module> findModulesByCours(Long coursId) {
        return moduleRepository.findByCoursIdOrderByOrdre(coursId);
    }

    public List<Lecon> findLeconsByModule(Long moduleId) {
        return leconRepository.findByModuleIdOrderByOrdre(moduleId);
    }

    public boolean isInscrit(Long apprenantId, Long coursId) {
        return inscriptionRepository.findByApprenantIdAndCoursId(apprenantId, coursId).isPresent();
    }
}
