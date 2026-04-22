package ru.yandex.practicum;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Wordle {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (PrintWriter log = new PrintWriter(new FileWriter("Log.txt"), true)) {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(log);
            WordleDictionary words = wordleDictionaryLoader.LoadWordDict();
            WordleGame game = new WordleGame(words, 6, words.generateAnswer(), log);
            System.out.println("Слово загадано");
            while (!game.isGameEnd()) {
                game.startGame();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
