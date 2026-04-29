package com.fst.learning.web;

import com.fst.learning.entity.Lesson;
import com.fst.learning.repository.LessonRepository;
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
@RequestMapping("/ui/lessons")
public class LessonUiController {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public LessonUiController(LessonRepository lessonRepository, ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("lessons", lessonRepository.findAll());
        return "ui/lessons/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("modules", moduleRepository.findAll());
        return "ui/lessons/form";
    }

    @PostMapping
    public String create(@ModelAttribute Lesson lesson, @RequestParam(required = false) Long moduleId) {
        if (moduleId != null) {
            lesson.setModule(moduleRepository.findById(moduleId).orElse(null));
        } else {
            lesson.setModule(null);
        }
        lessonRepository.save(lesson);
        return "redirect:/ui/lessons";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        model.addAttribute("lesson", lesson);
        model.addAttribute("modules", moduleRepository.findAll());
        return "ui/lessons/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Lesson lesson, @RequestParam(required = false) Long moduleId) {
        lesson.setId(id);
        if (moduleId != null) {
            lesson.setModule(moduleRepository.findById(moduleId).orElse(null));
        } else {
            lesson.setModule(null);
        }
        lessonRepository.save(lesson);
        return "redirect:/ui/lessons";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        lessonRepository.deleteById(id);
        return "redirect:/ui/lessons";
    }
}

