package com.example;

import java.util.List;
import java.util.Scanner;

public class PaginationUtil {

    public static <T> void paginateAndDisplay(List<T> items, String header, Scanner scanner) {
        if (items.isEmpty()) {
            System.out.println("Нет данных в базе.");
            return;
        }

        int pageSize = 10;
        int totalRecords = items.size();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        int currentPage = 1;

        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalRecords);

            System.out.println("\n" + header);
            System.out.println("Страница " + currentPage + " из " + totalPages);

            for (int i = start; i < end; i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }

            System.out.println("\n1. Следующая страница");
            System.out.println("2. Предыдущая страница");
            System.out.println("3. Выйти");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            if (choice == 1) {
                if (currentPage < totalPages) {
                    currentPage++;
                } else {
                    System.out.println("Это последняя страница.");
                }
            } else if (choice == 2) {
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    System.out.println("Это первая страница.");
                }
            } else if (choice == 3) {
                System.out.println("Возврат в меню.");
                break;
            } else {
                System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }
}