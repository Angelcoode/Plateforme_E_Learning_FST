package com.fst.elearning.web;

import com.fst.elearning.entity.Lecon;
import com.fst.elearning.service.LeconService;
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
@RequestMapping("/ui/lecons")
public class LeconUiController {

    private final LeconService leconService;
    private final ModuleService moduleService;

    public LeconUiController(LeconService leconService, ModuleService moduleService) {
        this.leconService = leconService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("lecons", leconService.findAll());
        return "ui/lecons/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("lecon", new Lecon());
        model.addAttribute("modules", moduleService.findAll());
        return "ui/lecons/form";
    }

    @PostMapping
    public String create(@ModelAttribute Lecon lecon, @RequestParam(required = false) Long moduleId) {
        if (moduleId != null) {
            lecon.setModule(moduleService.findById(moduleId).orElse(null));
        } else {
            lecon.setModule(null);
        }
        leconService.save(lecon);
        return "redirect:/ui/lecons";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Lecon lecon = leconService.findById(id).orElseThrow();
        model.addAttribute("lecon", lecon);
        model.addAttribute("modules", moduleService.findAll());
        return "ui/lecons/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Lecon lecon, @RequestParam(required = false) Long moduleId) {
        lecon.setId(id);
        if (moduleId != null) {
            lecon.setModule(moduleService.findById(moduleId).orElse(null));
        } else {
            lecon.setModule(null);
        }
        leconService.save(lecon);
        return "redirect:/ui/lecons";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        leconService.deleteById(id);
        return "redirect:/ui/lecons";
    }
}

