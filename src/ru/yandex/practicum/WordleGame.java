package ru.yandex.practicum;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps;

    int tryes = 0;

    private WordleDictionary dictionary;

    Scanner scanner = new Scanner(System.in);

    Random random = new Random();

    public WordleGame(WordleDictionary dictionary, int steps, String answer) {
        this.dictionary = dictionary;
        this.steps = steps;
        this.answer = answer;
    }

    public void startGame() {
        System.out.println("Слово загадано!");
        System.out.println("Для глупых загаданное слово: " + answer);
        while (tryes < steps) {
            try {
                System.out.println("Попробуйте угадать");
                String answer = scanner.nextLine();
                System.out.println(checkAnswer(answer));
                tryes++;
            } catch (InputMismatchException e) {
                System.out.println("Введено не число.");
            }
        }
    }
    
    public String checkAnswer(String ans) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ans.length(); i++) {
            if (answer.indexOf(ans.charAt(i)) >= 0 && answer.indexOf(ans.charAt(i)) == ans.indexOf(ans.charAt(i))) {
                str.append("+");
            } else if (answer.indexOf(ans.charAt(i)) >= 0) {
                str.append("^");
            } else {
                str.append("-");
            }
        }
        return str.toString();
    }
}
