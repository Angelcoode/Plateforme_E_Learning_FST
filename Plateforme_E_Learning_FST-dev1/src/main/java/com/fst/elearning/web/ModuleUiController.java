package com.fst.elearning.web;

import com.fst.elearning.entity.Module;
import com.fst.elearning.service.CoursService;
import com.fst.elearning.service.ModuleService;
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

    private final ModuleService moduleService;
    private final CoursService coursService;

    public ModuleUiController(ModuleService moduleService, CoursService coursService) {
        this.moduleService = moduleService;
        this.coursService = coursService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("modules", moduleService.findAll());
        return "ui/modules/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("module", new Module());
        model.addAttribute("cours", coursService.findAll());
        return "ui/modules/form";
    }

    @PostMapping
    public String create(@ModelAttribute Module module, @RequestParam(required = false) Long coursId) {
        if (coursId != null) {
            module.setCours(coursService.findById(coursId).orElse(null));
        } else {
            module.setCours(null);
        }
        moduleService.save(module);
        return "redirect:/ui/modules";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Module module = moduleService.findById(id).orElseThrow();
        model.addAttribute("module", module);
        model.addAttribute("cours", coursService.findAll());
        return "ui/modules/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Module module, @RequestParam(required = false) Long coursId) {
        module.setId(id);
        if (coursId != null) {
            module.setCours(coursService.findById(coursId).orElse(null));
        } else {
            module.setCours(null);
        }
        moduleService.save(module);
        return "redirect:/ui/modules";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        moduleService.deleteById(id);
        return "redirect:/ui/modules";
    }
}

