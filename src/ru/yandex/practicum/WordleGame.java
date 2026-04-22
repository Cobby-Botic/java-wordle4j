package ru.yandex.practicum;

import java.util.*;

public class WordleGame {

    private final String answer;
    private final int steps;
    private int tries = 0;

    private final Map<String, String> answers = new LinkedHashMap<>();
    private final WordleDictionary dictionary;

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public WordleGame(WordleDictionary dictionary, int steps, String answer) {
        this.dictionary = dictionary;
        this.steps = steps;
        this.answer = answer;
    }

    public void startGame() {
        System.out.println("Слово загадано!");
        System.out.println("Для глупых загаданное слово: " + answer);

        while (!isGameEnd()) {
            System.out.println("\nПопробуйте угадать (пустая строка — подсказка):");
            String guess = scanner.nextLine();

            if (guess.isEmpty()) {
                // спецкоманда: подсказка
                String hint = gameHint();
                if ("Нет подходящих слов".equals(hint)) {
                    System.out.println(hint);
                } else {
                    System.out.println("Подсказка: " + hint);
                    String result = checkAnswer(hint);
                    System.out.println(result);
                    tries++;
                }
                continue;
            }

            try {
                validateGuess(guess);
                String result = checkAnswer(guess);
                System.out.println(result);
                tries++;
            } catch (InvalidGuessException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }
        }
    }

    private void validateGuess(String guess) throws InvalidGuessException {
        if (guess.length() != answer.length()) {
            throw new InvalidGuessException(
                    "Слово должно быть длиной " + answer.length() + " букв(ы)."
            );
        }

        if (!guess.matches("[А-Яа-яЁё]+")) {
            throw new InvalidGuessException(
                    "Можно вводить только русские буквы без пробелов, цифр и латиницы."
            );
        }

        if (!dictionary.getWords().contains(guess)) {
            throw new InvalidGuessException("Такого слова нет в словаре.");
        }
    }

    public String checkAnswer(String guess) {
        String result = check(guess, answer);
        answers.put(guess, result);
        return result;
    }

    public String check(String guess, String target) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            char g = guess.charAt(i);
            char t = target.charAt(i);

            if (t == g) {
                str.append("+");
            } else if (target.indexOf(g) >= 0) {
                str.append("^");
            } else {
                str.append("-");
            }
        }
        return str.toString();
    }

    public String gameHint() {
        if (answers.isEmpty()) {
            return dictionary.generateAnswer();
        }

        List<String> candidates = new ArrayList<>();
        for (String word : dictionary.getWords()) {

            boolean isValid = true;

            for (Map.Entry<String, String> entry : answers.entrySet()) {
                String guess = entry.getKey();      // что вводил пользователь
                String expected = entry.getValue(); // какой результат был

                String actual = check(guess, word);

                if (!actual.equals(expected)) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                candidates.add(word);
            }
        }

        if (candidates.isEmpty()) {
            return "Нет подходящих слов";
        }

        return candidates.get(random.nextInt(candidates.size()));
    }

    public boolean checkWord(String word) {
        return dictionary.getWords().contains(word);
    }

    public boolean isGameEnd() {
        String winPattern = "+++++";

        if (tries >= steps) {
            System.out.println("\nИгра окончена. Загаданное слово: " + answer);
            return true;
        }

        for (String result : answers.values()) {
            if (winPattern.equals(result)) {
                System.out.println("\nВы угадали слово! Это было: " + answer);
                return true;
            }
        }
        return false;
    }
}