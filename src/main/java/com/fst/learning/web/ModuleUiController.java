package com.fst.learning.web;

import com.fst.learning.entity.Module;
import com.fst.learning.repository.CoursRepository;
import com.fst.learning.repository.ModuleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/modules")
public class ModuleUiController {

    private final ModuleRepository moduleRepository;
    private final CoursRepository coursRepository;

    public ModuleUiController(ModuleRepository moduleRepository, CoursRepository coursRepository) {
        this.moduleRepository = moduleRepository;
        this.coursRepository = coursRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("modules", moduleRepository.findAll());
        return "ui/modules/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("module", new Module());
        model.addAttribute("cours", coursRepository.findAll());
        return "ui/modules/form";
    }

    @PostMapping
    public String create(@ModelAttribute Module module, @RequestParam(required = false) Long coursId) {
        if (coursId != null) {
            module.setCours(coursRepository.findById(coursId).orElse(null));
        } else {
            module.setCours(null);
        }
        moduleRepository.save(module);
        return "redirect:/ui/modules";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Module module = moduleRepository.findById(id).orElseThrow();
        model.addAttribute("module", module);
        model.addAttribute("cours", coursRepository.findAll());
        return "ui/modules/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Module module, @RequestParam(required = false) Long coursId) {
        module.setId(id);
        if (coursId != null) {
            module.setCours(coursRepository.findById(coursId).orElse(null));
        } else {
            module.setCours(null);
        }
        moduleRepository.save(module);
        return "redirect:/ui/modules";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        moduleRepository.deleteById(id);
        return "redirect:/ui/modules";
    }
}

