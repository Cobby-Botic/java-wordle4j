package ru.yandex.practicum;

public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}

class InvalidGuessException extends GameException {
    public InvalidGuessException(String message) {
        super(message);
    }
}