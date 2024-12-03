package com.example.ui;

import com.example.model.Department;
import com.example.service.DepartmentService;
import com.example.util.PaginationUtil;
import com.example.util.ConsoleUtils;


import java.util.List;
import java.util.Scanner;

public class DepartmentUI {

    private final DepartmentService departmentService;
    private final Scanner scanner;

    public DepartmentUI(DepartmentService departmentService) {
        this.departmentService = departmentService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            System.out.println("1. Просмотр всех департаментов");
            System.out.println("2. Получить департамент по ID");
            System.out.println("3. Добавить новый департамент");
            System.out.println("4. Обновить департамент");
            System.out.println("5. Удалить департамент");
            System.out.println("6. Поиск департамента");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Очистка буфера

            switch (choice) {
                case 1:
                    ConsoleUtils.clearScreen();
                    viewAllDepartments();
                    break;
                case 2:
                    ConsoleUtils.clearScreen();
                    viewDepartmentById();
                    break;
                case 3:
                    ConsoleUtils.clearScreen();
                    addDepartment();
                    break;
                case 4:
                    ConsoleUtils.clearScreen();
                    updateDepartment();
                    break;
                case 5:
                    ConsoleUtils.clearScreen();
                    deleteDepartment();
                    break;
                case 6:
                    ConsoleUtils.clearScreen();
                    searchDepartment();
                    break;
                case 0:
                    ConsoleUtils.clearScreen();
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        PaginationUtil.paginateAndDisplay(departments, "=== Список департаментов ===", scanner);
    }

    private void viewDepartmentById() {
        System.out.print("Введите ID департамента: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // Очистка буфера
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            System.out.println(department);
            ConsoleUtils.waitForEnter();
        } else {
            System.out.println("Департамент с таким ID не найден.");
            ConsoleUtils.waitForEnter();
        }
    }

    private void addDepartment() {
        System.out.print("Введите название департамента: ");
        String departmentName = scanner.nextLine();
        Department department = new Department();
        department.setDepartment(departmentName);  // Изменено
        departmentService.addDepartment(department);
        System.out.println("Департамент добавлен.");
        ConsoleUtils.waitForEnter();
    }

    private void updateDepartment() {
        System.out.print("Введите ID департамента для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Очистка буфера
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            System.out.print("Введите новое название департамента: ");
            String newName = scanner.nextLine();
            department.setDepartment(newName);
            departmentService.updateDepartment(department);
            System.out.println("Департамент обновлен.");
            ConsoleUtils.waitForEnter();
        } else {
            System.out.println("Департамент с таким ID не найден.");
            ConsoleUtils.waitForEnter();
        }
    }

    private void deleteDepartment() {
        System.out.print("Введите ID департамента для удаления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // Очистка буфера
        departmentService.deleteDepartment(id);
        System.out.println("Департамент удалён.");
        ConsoleUtils.waitForEnter();
    }

    private void searchDepartment() {
        System.out.print("Введите запрос для поиска: ");
        String query = scanner.nextLine();
        List<Department> departments = departmentService.searchDepartments(query);
        if (departments.isEmpty()) {
            System.out.println("Департаменты по данному запросу не найдены.");
            ConsoleUtils.waitForEnter();
        } else {
            departments.forEach(System.out::println);
            ConsoleUtils.waitForEnter();
        }
    }
}
