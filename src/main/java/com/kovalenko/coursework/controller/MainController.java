package com.kovalenko.coursework.controller;

import com.kovalenko.coursework.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String initialize() {
        return "index";
    }

    @PostMapping("/")
    public String getCorpus(Model model, @RequestBody MultipartFile text) {
        model.addAttribute("stat", mainService.generateStat(text));
        return "index";
    }
}
