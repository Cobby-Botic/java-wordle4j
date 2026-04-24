package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {

    private final String answer;
    private final PrintWriter log;
    private int tries = 0;
    private final int wordLength = 5;
    private final int maxSteps = 6;

    private Set<Character> absentLetters = new HashSet<>(); // буквы которых нет
    private Set<Character> presentLetters = new HashSet<>(wordLength); // буквы которые есть
    private List<Character> correctPositions = new ArrayList<>(
            Collections.nCopies(wordLength, null)); // правильные позиции
    private final Set<String> usedHints = new HashSet<>(); // для хранения использованных подсказок

    private final WordleDictionary dictionary;
    private final Random random = new Random();

    public WordleGame(String answer, PrintWriter log, WordleDictionary dictionary) {
        this.answer = answer;
        this.log = log;
        this.dictionary = dictionary;
    }

    public String processGuess(String guess) throws InvalidGuessException {
        if (guess.isBlank()) {
            String hint = gameHint();
            log.println("Пользователь запросил подсказку");

            if ("Нет подходящих слов".equals(hint)) {
                return hint;
            }

            String result = checkAnswer(hint);
            tries++;
            return "Подсказка: " + hint + "\n" + result;
        }
        String rightGuess = guess.trim().toLowerCase(Locale.ROOT).replace("ё", "е"); //подгоняю ввод
        // под правила
        validateGuess(rightGuess);
        String result = checkAnswer(rightGuess);
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
        usedHints.add(guess);
        return result;
    }

    public String check(String guess, String target) {
        char[] targetChar = target.toCharArray();
        boolean[] used = new boolean[wordLength];
        StringBuilder str = new StringBuilder("-".repeat(wordLength));

        for (int i = 0; i < wordLength; i++) {
            if (guess.charAt(i) == targetChar[i]) {
                str.setCharAt(i, '+');
                presentLetters.add(guess.charAt(i));
                correctPositions.set(i, guess.charAt(i));
                used[i] = true;
            }
        }

        for (int i = 0; i < wordLength; i++) {
            boolean found = false;
            if (str.charAt(i) == '+') {
                continue;
            }

            for (int j = 0; j < wordLength; j++) {
                if (target.charAt(j) == guess.charAt(i) && !used[j]) {
                    str.setCharAt(i, '^');
                    presentLetters.add(guess.charAt(i));
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                char g = guess.charAt(i);

                if (!presentLetters.contains(g)) {
                    absentLetters.add(g);
                }
            }
        }
        return str.toString();
    }

    public String gameHint() {

        if (usedHints.isEmpty()) {
            return dictionary.generateAnswer();
        }

        List<String> candidates = new ArrayList<>();

        for (String word : dictionary.getWords()) {
            boolean isValid = true;

            if (usedHints.contains(word)) {
                isValid = false;
                continue;
            }

            for (char c : absentLetters) {
                if (word.indexOf(c) >= 0) {
                    isValid = false;
                    break;
                }
            }
            if (!isValid) continue;

            for (int i = 0; i < wordLength; i++) {
                Character correct = correctPositions.get(i);
                if (correct != null && word.charAt(i) != correct) {
                    isValid = false;
                    break;
                }
            }
            if (!isValid) continue;

            for (char c : presentLetters) {
                if (word.indexOf(c) < 0) {
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
        String candidate = candidates.get(random.nextInt(candidates.size()));
        usedHints.add(candidate);
        return candidate;
    }

    public boolean isWin() {
        if (usedHints.contains(answer)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameEnd() {
        if (tries >= maxSteps) {
            log.println("Попытки закончились");
            return true;
        } else return usedHints.contains(answer);
    }

    public String getAnswer() {
        return answer;
    }
}