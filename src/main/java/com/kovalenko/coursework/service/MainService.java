package com.kovalenko.coursework.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MainService {

    Map<String, Integer> generateStat(MultipartFile file);

}
