package ru.yandex.practicum;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        try (PrintWriter log = new PrintWriter(new FileWriter("Log.txt", StandardCharsets.UTF_8), true);
             Scanner scanner = new Scanner(System.in)) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dictionary = loader.loadWordDict();

            WordleGame game = new WordleGame(
                    dictionary,
                    6,
                    dictionary.generateAnswer(),
                    log
            );

            System.out.println("Слово загадано!");

            while (!game.isGameEnd()) {
                System.out.println("\nВведите слово (Enter — подсказка):");

                String input = scanner.nextLine();

                try {
                    String response = game.processGuess(input);
                    System.out.println(response);
                } catch (InvalidGuessException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }

            if (game.isWin()) {
                System.out.println("Вы угадали слово!");
            } else {
                System.out.println("Вы проиграли. Слово было: " + game.getAnswer());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка создания лог-файла");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}