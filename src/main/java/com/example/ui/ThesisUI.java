package com.example.ui;

import com.example.model.Thesis;
import com.example.service.ThesisService;
import com.example.util.ConsoleUtils;
import com.example.util.PaginationUtil;

import java.util.List;
import java.util.Scanner;

public class ThesisUI {

    private final ThesisService thesisService;
    private final Scanner scanner;

    public ThesisUI(ThesisService thesisService) {
        this.thesisService = thesisService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            ConsoleUtils.clearScreen();
            System.out.println("=== Меню: Дипломные работы ===");
            System.out.println("1. Просмотреть все работы");
            System.out.println("2. Найти работу по зачётной книжке");
            System.out.println("3. Добавить работу");
            System.out.println("4. Редактировать работу");
            System.out.println("5. Удалить работу");
            System.out.println("6. Найти работу");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после ввода числа

            switch (choice) {
                case 1:
                    displayAllTheses();
                    break;
                case 2:
                    viewThesisByGradeBook(scanner, thesisService);
                    break;
                case 3:
                    addThesis(scanner);
                    break;
                case 4:
                    updateThesis(scanner);
                    break;
                case 5:
                    deleteThesis(scanner);
                    break;
                case 6:
                    searchTheses(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void displayAllTheses() {
        List<Thesis> theses = thesisService.getAllTheses();
        PaginationUtil.paginateAndDisplay(theses, "=== Список работ ===", scanner);


    }

    private void viewThesisByGradeBook(Scanner scanner, ThesisService thesisService) {
        System.out.print("Введите номер зачётной книжки: ");
        String gradeBook = scanner.nextLine();
        try {
            Thesis thesis = thesisService.getThesisByGradeBook(gradeBook);
            if (thesis != null) {
                System.out.println(thesis);
                ConsoleUtils.waitForEnter();
            } else {
                System.out.println("Дипломная работа с таким номером зачётной книжки не найдена.");
                ConsoleUtils.waitForEnter();
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении дипломной работы: " + e.getMessage());
            ConsoleUtils.waitForEnter();
        }
    }
    

    private void addThesis(Scanner scanner) {
        try {
            System.out.println("Введите данные новой работы.");
            System.out.print("Номер зачётной книжки: ");
            String gradeBook = scanner.nextLine();
            System.out.print("Код учителя: ");
            String teacherCode = scanner.nextLine();
            System.out.print("Тема работы: ");
            String topic = scanner.nextLine();
    
            Thesis thesis = new Thesis();
            thesis.setGradeBook(gradeBook);
            thesis.setTeacherCode(teacherCode);
            thesis.setTopic(topic);
    
            thesisService.addThesis(thesis);
            System.out.println("Работа успешно добавлена.");
            ConsoleUtils.waitForEnter();
        } catch (Exception e) {
            System.out.println("Ошибка: Не удалось добавить работу. Проверьте корректность данных.");
            System.out.println("Подробнее: " + e.getMessage());
            ConsoleUtils.waitForEnter();
        }
    }
    

    private void updateThesis(Scanner scanner) {
        try {
            System.out.print("Введите номер зачётной книжки работы для редактирования: ");
            String gradeBook = scanner.nextLine();
    
            Thesis thesis = thesisService.getThesisByGradeBook(gradeBook);
            if (thesis == null) {
                System.out.println("Работа с таким номером не найдена.");
                ConsoleUtils.waitForEnter();
                return;
            }
    
            System.out.println("Введите новые данные (оставьте поле пустым для пропуска):");
            System.out.print("Код учителя (" + thesis.getTeacherCode() + "): ");
            String teacherCode = scanner.nextLine();
            System.out.print("Тема работы (" + thesis.getTopic() + "): ");
            String topic = scanner.nextLine();
    
            if (!teacherCode.isEmpty()) thesis.setTeacherCode(teacherCode);
            if (!topic.isEmpty()) thesis.setTopic(topic);
    
            thesisService.updateThesis(thesis);
            System.out.println("Работа успешно обновлена.");
            ConsoleUtils.waitForEnter();
        } catch (Exception e) {
            System.out.println("Ошибка: Не удалось обновить дипломную работу. Проверьте корректность данных.");
            System.out.println("Подробнее: " + e.getMessage());
            ConsoleUtils.waitForEnter();
        }
    }
    

    private void deleteThesis(Scanner scanner) {
        System.out.print("Введите номер зачётной книжки работы для удаления: ");
        String gradeBook =scanner.nextLine();

        Thesis thesis = thesisService.getThesisByGradeBook(gradeBook);
        if (thesis == null) {
            System.out.println("Работа с таким номером не найдена.");
            ConsoleUtils.waitForEnter();
            return;
        }

        thesisService.deleteThesis(gradeBook);
        System.out.println("Работа успешно удалена.");
        ConsoleUtils.waitForEnter();
    }

    private void searchTheses(Scanner scanner) {
        System.out.print("Введите ключевое слово для поиска: ");
        String query = scanner.nextLine();

        List<Thesis> results = thesisService.searchTheses(query);
        if (results.isEmpty()) {
            System.out.println("Работы не найдены.");
            ConsoleUtils.waitForEnter();
        } else {
            System.out.println("=== Результаты поиска ===");
            for (Thesis thesis : results) {
                System.out.println(thesis);
            }
            ConsoleUtils.waitForEnter();
        }
    }
}

