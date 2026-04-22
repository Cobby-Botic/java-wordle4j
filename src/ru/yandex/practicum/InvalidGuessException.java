package ru.yandex.practicum;

class InvalidGuessException extends GameException {
    public InvalidGuessException(String message) {
        super(message);
    }
}