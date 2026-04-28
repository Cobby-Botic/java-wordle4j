package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    class WordleGameTest {

        @Test
        void testCheckExactMatch() {
            WordleDictionary dict = new WordleDictionary(List.of("пафос"));
            PrintWriter log = new PrintWriter(new StringWriter());
            WordleGame game = new WordleGame("пафос", log, dict);
            String result = game.check("пафос", "пафос");
            assertEquals("+++++", result);
        }

        @Test
        void testValidateGuessThrowsExceptionForWrongLength() {
            WordleDictionary dict = new WordleDictionary(List.of("пафос"));
            PrintWriter log = new PrintWriter(new StringWriter());

            WordleGame game = new WordleGame("пафос", log, dict);

            assertThrows(InvalidGuessException.class, () -> {
                game.processGuess("кот"); // короткое слово
            });
        }

        @Test
        void testGameHintReturnsFilteredWord() throws InvalidGuessException {
            WordleDictionary dict = new WordleDictionary(
                    List.of("пафос", "понос", "насос")
            );
            PrintWriter log = new PrintWriter(new StringWriter());
            WordleGame game = new WordleGame("пафос", log, dict);

            game.processGuess("понос");

            String hintResponse = game.processGuess("");
            String hint = hintResponse.split("\n")[0].replace("Подсказка: ", "");

            if (hint.equals("Нет подходящих слов")) {
                return;
            }

            assertTrue(dict.getWords().contains(hint));
            assertNotEquals("понос", hint);
        }

        @Test
        void testGameEndsOnWin() throws InvalidGuessException {
            WordleDictionary dict = new WordleDictionary(List.of("пафос"));
            PrintWriter log = new PrintWriter(new StringWriter());
            WordleGame game = new WordleGame("пафос", log, dict);
            game.processGuess("пафос");
            assertTrue(game.isGameEnd());
            assertTrue(game.isWin());
        }

        @Test
        void testGameEndsOnTriesLimit() throws InvalidGuessException {
            WordleDictionary dict = new WordleDictionary(
                    List.of("пафос", "понос", "насос", "лапша", "масло", "камни", "рекаа")
            );
            PrintWriter log = new PrintWriter(new StringWriter());
            WordleGame game = new WordleGame("пафос", log, dict);
            game.processGuess("понос");
            game.processGuess("насос");
            game.processGuess("лапша");
            game.processGuess("масло");
            game.processGuess("камни");
            game.processGuess("лапша");
            assertTrue(game.isGameEnd());
            assertFalse(game.isWin());
        }

        @Test
        void testEmptyInputGivesHint() throws InvalidGuessException {
            WordleDictionary dict = new WordleDictionary(
                    List.of("пафос", "понос", "насос")
            );
            PrintWriter log = new PrintWriter(new StringWriter());
            WordleGame game = new WordleGame("пафос", log, dict);
            String response = game.processGuess("");
            assertTrue(response.contains("Подсказка"));
        }
    }