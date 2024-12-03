package com.example.util;

import java.util.Scanner;


public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            System.out.println("Не удалось очистить экран.");
        }
    }

    public static void waitForEnter() {
        System.out.println("Нажмите Enter, чтобы продолжить...");
        scanner.nextLine();
    }
}
