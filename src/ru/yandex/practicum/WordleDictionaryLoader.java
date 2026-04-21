package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

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

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            System.out.println("Произошла ошибка");
        }
        return new WordleDictionary(words);
    }
}
