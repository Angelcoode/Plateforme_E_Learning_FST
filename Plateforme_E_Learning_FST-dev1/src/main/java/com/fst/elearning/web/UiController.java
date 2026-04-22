package com.fst.elearning.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class UiController {

    @GetMapping
    public String dashboard() {
        return "ui/dashboard";
    }
}

