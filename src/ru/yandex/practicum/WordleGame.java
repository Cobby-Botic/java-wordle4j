package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {

    private final String answer;
    private final int steps;
    private int tries = 0;
    private final PrintWriter log;

    private final Map<String, String> answers = new LinkedHashMap<>();
    private final WordleDictionary dictionary;
    private final Random random = new Random();

    public WordleGame(WordleDictionary dictionary, int steps, String answer, PrintWriter log) {
        this.dictionary = dictionary;
        this.steps = steps;
        this.answer = answer;
        this.log = log;
    }

    public String processGuess(String guess) throws InvalidGuessException {
        if (guess.isEmpty()) {
            String hint = gameHint();
            log.println("Пользователь запросил подсказку");

            if ("Нет подходящих слов".equals(hint)) {
                return hint;
            }

            String result = checkAnswer(hint);
            tries++;
            return "Подсказка: " + hint + "\n" + result;
        }

        validateGuess(guess);
        String result = checkAnswer(guess);
        tries++;
        log.println("Попытка: " + guess + " → " + result);
        return result;
    }

    private void validateGuess(String guess) throws InvalidGuessException {
        if (guess.length() != answer.length()) {
            throw new InvalidGuessException(
                    "Слово должно быть длиной " + answer.length()
            );
        }

        if (!guess.matches("[А-Яа-яЁё]+")) {
            throw new InvalidGuessException("Только русские буквы");
        }

        if (!dictionary.getWords().contains(guess)) {
            throw new InvalidGuessException("Слова нет в словаре");
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
                String guess = entry.getKey();
                String expected = entry.getValue();

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

    public boolean isGameEnd() {
        if (tries >= steps) {
            log.println("Попытки закончились");
            return true;
        }
        return answers.containsValue("+++++");
    }

    public boolean isWin() {
        return answers.containsValue("+++++");
    }

    public String getAnswer() {
        return answer;
    }
}