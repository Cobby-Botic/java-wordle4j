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

    public WordleDictionary LoadWordDict() {
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
            log.println("Словарь загружен.");

        } catch (FileNotFoundException e) {
            log.println("Файл не найден!");
        } catch (IOException e) {
            log.println("Произошла ошибка");
        }
        return new WordleDictionary(words);
    }
}
