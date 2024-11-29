package com.example.ui;

import com.example.model.Teacher;
import com.example.model.Degree;
import com.example.model.Rank;
import com.example.model.Department;
import com.example.service.TeacherService;

import java.util.List;
import java.util.Scanner;

public class TeacherUI {
    private final TeacherService teacherService;

    public TeacherUI(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== Меню: Учителя ===");
            System.out.println("1. Просмотреть всех учителей");
            System.out.println("2. Добавить учителя");
            System.out.println("3. Редактировать учителя");
            System.out.println("4. Удалить учителя");
            System.out.println("5. Найти учителя");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после ввода числа

            switch (choice) {
                case 1: {
                    displayAllTeachers();
                    break;
                }
                case 2: {
                    addTeacher(scanner);
                    break;
                }
                case 3: {
                    updateTeacher(scanner);
                    break;
                }
                case 4: {
                    deleteTeacher(scanner);
                    break;
                }
                case 5: {
                    searchTeachers(scanner);
                    break;
                }
                case 0: {
                    running = false;
                    break;
                }
                default: {
                    System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        }
    }

    private void displayAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        System.out.println("=== Список учителей ===");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    private void addTeacher(Scanner scanner) {
        try {
            System.out.println("Введите данные нового учителя.");
            System.out.print("Код учителя: ");
            String teacherCode = scanner.nextLine();
            System.out.print("ФИО: ");
            String fullName = scanner.nextLine();
            System.out.print("ID степени: ");
            Long degreeId = scanner.nextLong();
            System.out.print("ID звания: ");
            Long rankId = scanner.nextLong();
            System.out.print("ID кафедры: ");
            Long departmentId = scanner.nextLong();
            scanner.nextLine(); // Очистка буфера после ввода числа
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Degree degree = new Degree();
            degree.setDegreeId(degreeId);

            Rank rank = new Rank();
            rank.setRankId(rankId);

            Department department = new Department();
            department.setDepartmentId(departmentId);

            Teacher teacher = new Teacher();
            teacher.setTeacherCode(teacherCode);
            teacher.setFullName(fullName);
            teacher.setDegree(degree);
            teacher.setRank(rank);
            teacher.setDepartment(department);
            teacher.setPhone(phone);
            teacher.setEmail(email);

            teacherService.addTeacher(teacher);
            System.out.println("Учитель успешно добавлен.");
        } catch (Exception e) {
            System.out.println("Ошибка: Не удалось добавить учителя. Проверьте корректность данных.");
            System.out.println("Подробнее: " + e.getMessage());
        }
    }

    private void updateTeacher(Scanner scanner) {
        System.out.print("Введите код учителя для редактирования: ");
        String teacherCode = scanner.nextLine();

        Teacher teacher = teacherService.getTeacherByCode(teacherCode);
        if (teacher == null) {
            System.out.println("Учитель с таким кодом не найден.");
            return;
        }
        try {
            System.out.println("Введите новые данные (оставьте поле пустым для пропуска):");
            System.out.print("ФИО (" + teacher.getFullName() + "): ");
            String fullName = scanner.nextLine();
            System.out.print("ID степени (" + teacher.getDegree().getDegreeId() + "): ");
            String degreeIdInput = scanner.nextLine();
            System.out.print("ID звания (" + teacher.getRank().getRankId() + "): ");
            String rankIdInput = scanner.nextLine();
            System.out.print("ID кафедры (" + teacher.getDepartment().getDepartmentId() + "): ");
            String departmentIdInput = scanner.nextLine();
            System.out.print("Телефон (" + teacher.getPhone() + "): ");
            String phone = scanner.nextLine();
            System.out.print("Email (" + teacher.getEmail() + "): ");
            String email = scanner.nextLine();

            if (!fullName.isEmpty()) teacher.setFullName(fullName);
            if (!degreeIdInput.isEmpty()) {
                Degree degree = new Degree();
                degree.setDegreeId(Long.parseLong(degreeIdInput));
                teacher.setDegree(degree);
            }
            if (!rankIdInput.isEmpty()) {
                Rank rank = new Rank();
                rank.setRankId(Long.parseLong(rankIdInput));
                teacher.setRank(rank);
            }
            if (!departmentIdInput.isEmpty()) {
                Department department = new Department();
                department.setDepartmentId(Long.parseLong(departmentIdInput));
                teacher.setDepartment(department);
            }
            if (!phone.isEmpty()) teacher.setPhone(phone);
            if (!email.isEmpty()) teacher.setEmail(email);

            teacherService.updateTeacher(teacher);
            System.out.println("Учитель успешно обновлён.");
        } catch (Exception e) {
            System.out.println("Ошибка: Не удалось добавить учителя. Проверьте корректность данных.");
            System.out.println("Подробнее: " + e.getMessage());
        }
    }
    
    private void deleteTeacher(Scanner scanner) {
        System.out.print("Введите код учителя для удаления: ");
        String teacherCode = scanner.nextLine();

        teacherService.deleteTeacher(teacherCode);
        System.out.println("Учитель успешно удалён.");
    }

    private void searchTeachers(Scanner scanner) {
        System.out.print("Введите строку для поиска: ");
        String query = scanner.nextLine();

        List<Teacher> teachers = teacherService.searchTeachers(query);
        System.out.println("=== Результаты поиска ===");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }
}
