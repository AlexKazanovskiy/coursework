package com.kovalenko.coursework.service.impl;

import com.kovalenko.coursework.model.Corpus;
import com.kovalenko.coursework.model.WordType;
import com.kovalenko.coursework.service.MainService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    private final Map<String, WordType> dictionary = new HashMap<>();

    @PostConstruct
    public void readDictionary() throws IOException {
        String currentFolder = System.getProperty("user.dir");
        List<File> filesInFolder = Files.walk(Paths.get(currentFolder))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith(".txt"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        filesInFolder.forEach(file -> {
            try {
                List<String> strings = parseDictionary(new FileInputStream(file));
                for (String line : strings) {
                    String[] words = line.split("[\\s]");
                    dictionary.put(words[0], resolveWordType(words[1]));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private WordType resolveWordType(String word) {
        switch (word) {
            case "article":
                return WordType.A;
            case "B":
                return WordType.B;
            case "C":
                return WordType.C;
            default:
                return WordType.UNDEFINED;
        }
    }

    @Override
    public Corpus generateStat(InputStream file) {
        List<String> strings = parseFile(file);

        Map<String, Integer> map = new TreeMap<>();

        strings.forEach(s -> {
            map.putIfAbsent(s, 0);
            map.put(s, map.get(s) + 1);
        });

        Corpus corpus = new Corpus();
        corpus.setDictionary(dictionary);
        corpus.addWords(strings);
        corpus.calculateFrequency();

        return corpus;
    }


    private List<String> parseFile(InputStream file) {

        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                words.addAll(Arrays.asList(line.split("[,;\":.~!-?\\s]+")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private List<String> parseDictionary(InputStream file) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                words.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
