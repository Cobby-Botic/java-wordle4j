package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public String generateAnswer() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}
