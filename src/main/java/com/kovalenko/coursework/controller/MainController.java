package com.kovalenko.coursework.controller;

import com.kovalenko.coursework.service.MainService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController

public class MainController {

    private final MainService mainService;


    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/")
    public Map<String, Integer> getCorpus(@RequestBody MultipartFile text) {
        return mainService.generateStat(text);
    }
}
