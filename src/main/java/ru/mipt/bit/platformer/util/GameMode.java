package ru.mipt.bit.platformer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public enum GameMode {
    DEFAULT, RANDOM, FROM_FILE;

    public static GameMode getGameMode() {
        File file = new File("src/main/resources/mode");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int mode = scanner.nextInt();
        scanner.close();
        return switch (mode) {
            case 1 -> DEFAULT;
            case 2 -> RANDOM;
            case 3 -> FROM_FILE;
            default -> throw new RuntimeException();
        };
    }
}
