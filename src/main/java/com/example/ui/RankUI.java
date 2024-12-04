package com.example.ui;

import com.example.model.Rank;
import com.example.service.RankService;
import com.example.util.ConsoleUtils;
import com.example.util.PaginationUtil;


import java.util.List;
import java.util.Scanner;

public class RankUI {

    private final RankService rankService;
    private final Scanner scanner;

    public RankUI(RankService rankService) {
        this.rankService = rankService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            System.out.println("1. Просмотр всех званий");
            System.out.println("2. Получить звание по ID");
            System.out.println("3. Добавить новое звание");
            System.out.println("4. Обновить звание");
            System.out.println("5. Удалить звание");
            System.out.println("6. Поиск звания");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер после nextInt

            switch (choice) {
                case 1:
                    viewAllRanks();
                    break;
                case 2:
                    viewRankById();
                    break;
                case 3:
                    addRank();
                    break;
                case 4:
                    updateRank();
                    break;
                case 5:
                    deleteRank();
                    break;
                case 6:
                    searchRank();
                    break;
                case 0:
                    System.out.println("Выход...");
                    return;  // Выход из метода showMenu()
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewAllRanks() {
        List<Rank> ranks = rankService.getAllRanks();
        PaginationUtil.paginateAndDisplay(ranks, "=== Список званий ===", scanner);
    }

    private void viewRankById() {
        System.out.print("Введите ID звания: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // Очищаем буфер после nextLong()
        Rank rank = rankService.getRankById(id);
        if (rank != null) {
            System.out.println(rank);
            ConsoleUtils.waitForEnter();
        } else {
            System.out.println("Звание с таким ID не найдено.");
            ConsoleUtils.waitForEnter();
        }
    }

    private void addRank() {
        System.out.print("Введите название звания: ");
        String rankName = scanner.nextLine();
        Rank rank = new Rank();
        rank.setRank(rankName);
        rankService.addRank(rank);
        System.out.println("Звание добавлено.");
    }

    private void updateRank() {
        System.out.print("Введите ID звания для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // Очищаем буфер после nextLong()
        Rank rank = rankService.getRankById(id);
        if (rank != null) {
            System.out.print("Введите новое название звания: ");
            String rankName = scanner.nextLine();
            rank.setRank(rankName);
            rankService.updateRank(rank);
            System.out.println("Звание обновлено.");
        } else {
            System.out.println("Звание с таким ID не найдено.");
        }
    }

    private void deleteRank() {
        System.out.print("Введите ID ранга для удаления: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Очистка буфера
    
        try {
            rankService.deleteRank(id); // Вызов метода из Service
            System.out.println("Ранг успешно удален.");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    
        ConsoleUtils.waitForEnter(); // Ожидание нажатия Enter перед возвратом в меню
    }
    
    

    private void searchRank() {
        System.out.print("Введите запрос для поиска: ");
        String query = scanner.nextLine();
        List<Rank> ranks = rankService.searchRanks(query);
        if (ranks.isEmpty()) {
            System.out.println("Звания по данному запросу не найдены.");
        } else {
            for (Rank rank : ranks) {
                System.out.println(rank);
            }
            ConsoleUtils.waitForEnter();
        }
    }
}
