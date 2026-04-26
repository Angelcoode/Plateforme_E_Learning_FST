package com.fst.elearning.service;

import com.fst.elearning.entity.Module;
import com.fst.elearning.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    public Optional<Module> findById(Long id) {
        return moduleRepository.findById(id);
    }

    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    public void deleteById(Long id) {
        moduleRepository.deleteById(id);
    }
}
