package com.kovalenko.coursework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Corpus {

    private List<Word> words = new ArrayList<>();
    private int quantity = 0;
    private Map<String, WordType> dictionary;


    public void addWords(List<String> words) {
        for (String word : words) {

            this.quantity++;

            if (this.words.contains(new Word(word, dictionary.get(word)))) {
                this.words.get(this.words.indexOf(new Word(word, dictionary.get(word))))
                        .incrementValue();

                continue;
            }
            this.words.add(new Word(word, dictionary.get(word)));
        }
    }

    public void calculateFrequency() {
        this.words.forEach(word -> word.setFrequency(word.getNumber() / this.quantity));
    }

    @Data
    @EqualsAndHashCode(of = "value")
    public static class Word {
        private String value;
        private WordType type;
        private int number = 1;
        private double frequency;

        public Word(String value, WordType wordType) {
            this.value = value;
            this.type = wordType;
        }

        public void incrementValue() {
            this.number++;
        }
    }
}
