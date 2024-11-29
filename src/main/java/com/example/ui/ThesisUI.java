package com.example.ui;

import com.example.model.Thesis;
import com.example.service.ThesisService;

import java.util.List;
import java.util.Scanner;

public class ThesisUI {

    private final ThesisService thesisService;

    public ThesisUI(ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== Меню: Дипломные работы ===");
            System.out.println("1. Просмотреть все работы");
            System.out.println("2. Добавить работу");
            System.out.println("3. Редактировать работу");
            System.out.println("4. Удалить работу");
            System.out.println("5. Найти работу");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после ввода числа

            switch (choice) {
                case 1:
                    displayAllTheses();
                    break;
                case 2:
                    addThesis(scanner);
                    break;
                case 3:
                    updateThesis(scanner);
                    break;
                case 4:
                    deleteThesis(scanner);
                    break;
                case 5:
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
        System.out.println("=== Список дипломных работ ===");
        for (Thesis thesis : theses) {
            System.out.println(thesis);
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
        } catch (Exception e) {
            System.out.println("Ошибка: Не удалось добавить работу. Проверьте корректность данных.");
            System.out.println("Подробнее: " + e.getMessage());
        }
    }
    

    private void updateThesis(Scanner scanner) {
        try {
            System.out.print("Введите номер зачётной книжки работы для редактирования: ");
            String gradeBook = scanner.nextLine();
    
            Thesis thesis = thesisService.getThesisByGradeBook(gradeBook);
            if (thesis == null) {
                System.out.println("Работа с таким номером не найдена.");
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
        } catch (Exception e) {
            System.out.println("Ошибка: Не удалось обновить дипломную работу. Проверьте корректность данных.");
            System.out.println("Подробнее: " + e.getMessage());
        }
    }
    

    private void deleteThesis(Scanner scanner) {
        System.out.print("Введите номер зачётной книжки работы для удаления: ");
        String gradeBook =scanner.nextLine();

        Thesis thesis = thesisService.getThesisByGradeBook(gradeBook);
        if (thesis == null) {
            System.out.println("Работа с таким номером не найдена.");
            return;
        }

        thesisService.deleteThesis(gradeBook);
        System.out.println("Работа успешно удалена.");
    }

    private void searchTheses(Scanner scanner) {
        System.out.print("Введите ключевое слово для поиска: ");
        String query = scanner.nextLine();

        List<Thesis> results = thesisService.searchTheses(query);
        if (results.isEmpty()) {
            System.out.println("Работы не найдены.");
        } else {
            System.out.println("=== Результаты поиска ===");
            for (Thesis thesis : results) {
                System.out.println(thesis);
            }
        }
    }
}

