package ru.yandex.practicum;

import java.io.File;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {

        File log = new File("Log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();
        WordleDictionary words = wordleDictionaryLoader.LoadWordDict();
        WordleGame game = new WordleGame(words, 6, words.generateAnswer());
        game.startGame();
    }

}
