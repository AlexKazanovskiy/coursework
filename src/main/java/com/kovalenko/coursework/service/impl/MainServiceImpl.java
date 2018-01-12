package com.kovalenko.coursework.service.impl;

import com.kovalenko.coursework.service.MainService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class MainServiceImpl implements MainService {

    @Override
    public Map<String, Integer> generateStat(MultipartFile file) {
        List<String> strings = parseFile(file);

        Map<String, Integer> map = new TreeMap<>();

        strings.parallelStream().forEach(s -> {
            map.putIfAbsent(s, 0);
            map.put(s, map.get(s)+1);
        });

        return map;
    }

    @SneakyThrows
    private List<String> parseFile(MultipartFile file) {
        validateFile(file);

        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> words = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }

        return words;
    }

    private void validateFile(MultipartFile file) {
        if(!file.getContentType().equals("text/plain")) {
            throw new RuntimeException("Wrong content type");
        }
    }
}
