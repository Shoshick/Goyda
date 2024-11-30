package com.example.ui;

import com.example.model.Grade;
import com.example.service.GradeService;
import com.example.model.Student;
import com.example.PaginationUtil;


import java.util.List;
import java.util.Scanner;

public class GradeUI {

    private final GradeService gradeService;
    private final Scanner scanner;

    public GradeUI(GradeService gradeService) {
        this.gradeService = gradeService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("1. Просмотр всех оценок");
            System.out.println("2. Получить оценки по номеру зачетной книжки");
            System.out.println("3. Добавить новую оценку");
            System.out.println("4. Обновить оценку");
            System.out.println("5. Удалить оценку");
            System.out.println("6. Поиск оценок");
            System.out.println("7. Выход");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    viewAllGrades();
                    break;
                case 2:
                    viewGradeByGradeBook();
                    break;
                case 3:
                    addGrade();
                    break;
                case 4:
                    updateGrade();
                    break;
                case 5:
                    deleteGrade();
                    break;
                case 6:
                    searchGrades();
                    break;
                case 7:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewAllGrades() {    
        List<Grade> grades = gradeService.getAllGrades();
        PaginationUtil.paginateAndDisplay(grades, "=== Список оценок ===", scanner);
    }


    private void viewGradeByGradeBook() {
        System.out.print("Введите номер зачетной книжки: ");
        String gradeBook = scanner.nextLine();
        try {
            // Используем метод для получения одной оценки по зачетной книжке
            Grade grade = gradeService.getGradeByGradeBook(gradeBook);
            if (grade != null) {
                System.out.println(grade);
            } else {
                System.out.println("Оценки для этого студента не найдены.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении оценок: " + e.getMessage());
        }
    }

    private void addGrade() {
        System.out.print("Введите номер зачетной книжки студента: ");
        String gradeBook = scanner.nextLine();
        System.out.print("Введите оценку за экзамен: ");
        Integer examGrade = scanner.nextInt();
        System.out.print("Введите оценку за диплом: ");
        Integer diplomaGrade = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        try {
            // Получаем студента по зачетной книжке (если этот метод нужно добавить в сервис)
            Student student = gradeService.getStudentByGradeBook(gradeBook);

            if (student != null) {
                Grade grade = new Grade();
                grade.setStudent(student);
                grade.setExamGrade(examGrade);
                grade.setDiplomaGrade(diplomaGrade);

                gradeService.addGrade(grade);
                System.out.println("Оценка добавлена.");
            } else {
                System.out.println("Студент с таким номером зачетной книжки не найден.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении оценки: " + e.getMessage());
        }
    }

    private void updateGrade() {
        System.out.print("Введите номер зачетной книжки для обновления оценки: ");
        String gradeBook = scanner.nextLine();
        try {
            Grade grade = gradeService.getGradeByGradeBook(gradeBook);
            if (grade != null) {
                System.out.print("Введите новую оценку за экзамен: ");
                Integer examGrade = scanner.nextInt();
                System.out.print("Введите новую оценку за диплом: ");
                Integer diplomaGrade = scanner.nextInt();
                scanner.nextLine(); // consume the newline

                // Обновляем оценку
                grade.setExamGrade(examGrade);
                grade.setDiplomaGrade(diplomaGrade);
                gradeService.updateGrade(grade);

                System.out.println("Оценка обновлена.");
            } else {
                System.out.println("Оценки для этого студента не найдены.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении оценки: " + e.getMessage());
        }
    }

    private void deleteGrade() {
        System.out.print("Введите номер зачетной книжки для удаления оценки: ");
        String gradeBook = scanner.nextLine();
        try {
            Grade grade = gradeService.getGradeByGradeBook(gradeBook);
            if (grade != null) {
                gradeService.deleteGrade(gradeBook);
                System.out.println("Оценка удалена.");
            } else {
                System.out.println("Оценки для этого студента не найдены.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении оценки: " + e.getMessage());
        }
    }

    private void searchGrades() {
        System.out.print("Введите строку для поиска оценок: ");
        String searchTerm = scanner.nextLine();
        try {
            List<Grade> grades = gradeService.searchGrades(searchTerm);
            if (grades.isEmpty()) {
                System.out.println("Оценки не найдены.");
            } else {
                grades.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске оценок: " + e.getMessage());
        }
    }
}
