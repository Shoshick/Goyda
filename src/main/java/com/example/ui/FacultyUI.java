package com.example.ui;

import com.example.model.Faculty;
import com.example.service.FacultyService;

import java.util.List;
import java.util.Scanner;

public class FacultyUI {

    private final FacultyService facultyService;
    private final Scanner scanner;

    public FacultyUI(FacultyService facultyService) {
        this.facultyService = facultyService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("1. Просмотр всех факультетов");
            System.out.println("2. Получить факультет по ID");
            System.out.println("3. Добавить новый факультет");
            System.out.println("4. Обновить факультет");
            System.out.println("5. Удалить факультет");
            System.out.println("6. Поиск факультета");
            System.out.println("7. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllFaculties();
                    break;
                case 2:
                    viewFacultyById();
                    break;
                case 3:
                    addFaculty();
                    break;
                case 4:
                    updateFaculty();
                    break;
                case 5:
                    deleteFaculty();
                    break;
                case 6:
                    searchFaculty();
                    break;
                case 7:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewAllFaculties() {
        List<Faculty> faculties = facultyService.getAllFaculties();
        if (faculties.isEmpty()) {
            System.out.println("Нет факультетов в базе данных.");
        } else {
            for (Faculty faculty : faculties) {
                System.out.println(faculty);
            }
        }
    }

    private void viewFacultyById() {
        System.out.print("Введите ID факультета: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Faculty faculty = facultyService.getFacultyById(id);
        System.out.println(faculty != null ? faculty : "Факультет не найден.");
    }

    private void addFaculty() {
        System.out.print("Введите название факультета: ");
        String name = scanner.nextLine();
        Faculty faculty = new Faculty(name);
        facultyService.addFaculty(faculty);
        System.out.println("Факультет добавлен.");
    }

    private void updateFaculty() {
        System.out.print("Введите ID факультета: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty != null) {
            System.out.print("Введите новое название факультета: ");
            String name = scanner.nextLine();
            faculty.setFaculty(name);
            facultyService.updateFaculty(faculty);
            System.out.println("Факультет обновлен.");
        } else {
            System.out.println("Факультет не найден.");
        }
    }

    private void deleteFaculty() {
        System.out.print("Введите ID факультета: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        facultyService.deleteFaculty(id);
        System.out.println("Факультет удален.");
    }

    private void searchFaculty() {
        System.out.print("Введите запрос для поиска: ");
        String query = scanner.nextLine();
        List<Faculty> faculties = facultyService.searchFaculties(query);
        faculties.forEach(System.out::println);
    }
}
