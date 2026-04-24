package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final Random random = new Random();
    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public String generateAnswer() {
        return words.get(random.nextInt(words.size()));
    }
}
