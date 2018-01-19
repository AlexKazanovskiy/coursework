package com.kovalenko.coursework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Corpus {

    private List<Word> words = new ArrayList<>();
    private int quantity = 0;
    private int unknownWords = 0;
    private int uniqueWords = 0;
    private Map<String, WordType> dictionary = new HashMap<>();


    public void addWords(List<String> words) {
        for (String s : words) {

            s = s.toLowerCase();

            this.quantity++;

            WordType wordType = dictionary.get(s) == null ? WordType.UNDEFINED : dictionary.get(s);

            Word word = new Word(s, wordType);

            if(this.words.contains(word)) {
                this.words.get(this.words.indexOf(word))
                        .incrementValue();

                continue;
            }
            this.words.add(word);
            this.uniqueWords++;
        }
    }

    public void calculateFrequency() {
        this.words.forEach(word -> word.calculateFrequency(this.quantity));
    }


    @Data
    @EqualsAndHashCode(of = "value")
    public static class Word {
        private String value;
        private WordType type;
        private int quantity = 1;
        private double frequency;

        public Word(String value, WordType wordType) {
            this.value = value;
            this.type = wordType;
        }

        public void incrementValue() {
            this.quantity++;
        }

        public void calculateFrequency(double totalWords){
            this.frequency = quantity/totalWords;
        }
    }
}
