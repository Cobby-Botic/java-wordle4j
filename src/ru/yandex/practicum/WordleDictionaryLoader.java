package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    private PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary loadWordDict() {
        List<String> words = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
            while (bufferedReader.ready()) {
                String word = bufferedReader.readLine();
                if (word.length() == 5) {
                    String result = word.toLowerCase().replaceAll("ё", "е");
                    words.add(result);
                }
            }
        } catch (IOException e) {
            throw new DictionaryLoadException("Не удалось загрузить словарь!");
        }
        if (words.isEmpty()) {
            throw new DictionaryIsEmptyException("Словарь пуст продолжение игры невозможно");
        }
        log.println("Словарь загружен.");
        return new WordleDictionary(words);
    }
}
