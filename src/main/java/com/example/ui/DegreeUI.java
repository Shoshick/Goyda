package com.example.ui;

import com.example.model.Degree;
import com.example.service.DegreeService;
import com.example.PaginationUtil;


import java.util.List;
import java.util.Scanner;

public class DegreeUI {

    private final DegreeService degreeService;
    private final Scanner scanner;

    public DegreeUI(DegreeService degreeService) {
        this.degreeService = degreeService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("1. Просмотр всех степеней");
            System.out.println("2. Получить степень по ID");
            System.out.println("3. Добавить новую степень");
            System.out.println("4. Обновить степень");
            System.out.println("5. Удалить степень");
            System.out.println("6. Поиск степени");
            System.out.println("7. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();  // очистка буфера

            switch (choice) {
                case 1:
                    viewAllDegrees();
                    break;
                case 2:
                    viewDegreeById();
                    break;
                case 3:
                    addDegree();
                    break;
                case 4:
                    updateDegree();
                    break;
                case 5:
                    deleteDegree();
                    break;
                case 6:
                    searchDegree();
                    break;
                case 7:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewAllDegrees() {
        List<Degree> degrees = degreeService.getAllDegrees();
        PaginationUtil.paginateAndDisplay(degrees, "=== Список степеней ===", scanner);
    }
    

    private void viewDegreeById() {
        System.out.print("Введите ID степени: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // очистка буфера
        Degree degree = degreeService.getDegreeById(id);
        if (degree != null) {
            System.out.println(degree);
        } else {
            System.out.println("Степень с таким ID не найдена.");
        }
    }

    private void addDegree() {
        System.out.print("Введите название степени: ");
        String degreeName = scanner.nextLine();
        Degree degree = new Degree();
        degree.setDegree(degreeName);
        degreeService.addDegree(degree);
        System.out.println("Степень добавлена.");
    }

    private void updateDegree() {
        System.out.print("Введите ID степени для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // очистка буфера
        Degree degree = degreeService.getDegreeById(id);
        if (degree != null) {
            System.out.print("Введите новое название степени: ");
            String degreeName = scanner.nextLine();
            degree.setDegree(degreeName);
            degreeService.updateDegree(degree);
            System.out.println("Степень обновлена.");
        } else {
            System.out.println("Степень с таким ID не найдена.");
        }
    }

    private void deleteDegree() {
        System.out.print("Введите ID степени для удаления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // очистка буфера
        degreeService.deleteDegree(id);
        System.out.println("Степень удалена.");
    }

    private void searchDegree() {
        System.out.print("Введите запрос для поиска: ");
        String query = scanner.nextLine();
        List<Degree> degrees = degreeService.searchDegrees(query);
        if (degrees.isEmpty()) {
            System.out.println("Степени по данному запросу не найдены.");
        } else {
            for (Degree degree : degrees) {
                System.out.println(degree);
            }
        }
    }
}