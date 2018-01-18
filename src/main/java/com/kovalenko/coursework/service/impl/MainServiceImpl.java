package com.kovalenko.coursework.service.impl;

import com.kovalenko.coursework.model.Corpus;
import com.kovalenko.coursework.service.MainService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class MainServiceImpl implements MainService {

    @Override
    public Map<String, Integer> generateStat(MultipartFile file) {
        List<String> strings = parseFile(file);

        Map<String, Integer> map = new TreeMap<>();

        strings.forEach(s -> {
            map.putIfAbsent(s, 0);
            map.put(s, map.get(s) + 1);
        });

        Corpus corpus = new Corpus();
        corpus.addWords(strings);
        corpus.calculateFrequency();

        return map;
    }


    private List<String> parseFile(MultipartFile file) {


        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> words = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                words.addAll(Arrays.asList(line.split("[,;\":.~!-?\\s]+")));
            }

            return words;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
