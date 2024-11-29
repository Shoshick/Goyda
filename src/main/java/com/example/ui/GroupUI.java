package com.example.ui;

import com.example.model.Group;
import com.example.service.GroupService;

import java.util.List;
import java.util.Scanner;

public class GroupUI {

    private final GroupService groupService;
    private final Scanner scanner;

    public GroupUI(GroupService groupService) {
        this.groupService = groupService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("1. Просмотр всех групп");
            System.out.println("2. Получить группу по ID");
            System.out.println("3. Добавить новую группу");
            System.out.println("4. Обновить группу");
            System.out.println("5. Удалить группу");
            System.out.println("6. Поиск группы");
            System.out.println("7. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewAllGroups();
                    break;
                case 2:
                    viewGroupById();
                    break;
                case 3:
                    addGroup();
                    break;
                case 4:
                    updateGroup();
                    break;
                case 5:
                    deleteGroup();
                    break;
                case 6:
                    searchGroup();
                    break;
                case 7:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            System.out.println("Нет групп в базе данных.");
        } else {
            for (Group group : groups) {
                System.out.println(group);
            }
        }
    }

    private void viewGroupById() {
        System.out.print("Введите ID группы: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); 
        Group group = groupService.getGroupById(id);
        if (group != null) {
            System.out.println(group);
        } else {
            System.out.println("Группа с таким ID не найдена.");
        }
    }

    private void addGroup() {
        System.out.print("Введите название группы: ");
        String groupName = scanner.nextLine();
        Group group = new Group();
        group.setGroupName(groupName);
        groupService.addGroup(group);
        System.out.println("Группа добавлена.");
    }

    private void updateGroup() {
        System.out.print("Введите ID группы для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); 
        Group group = groupService.getGroupById(id);
        if (group != null) {
            System.out.print("Введите новое название группы: ");
            String groupName = scanner.nextLine();
            group.setGroupName(groupName);
            groupService.updateGroup(group);
            System.out.println("Группа обновлена.");
        } else {
            System.out.println("Группа с таким ID не найдена.");
        }
    }

    private void deleteGroup() {
        System.out.print("Введите ID группы для удаления: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); 
        groupService.deleteGroup(id);
        System.out.println("Группа удалена.");
    }

    private void searchGroup() {
        System.out.print("Введите запрос для поиска: ");
        String query = scanner.nextLine();
        List<Group> groups = groupService.searchGroups(query);
        if (groups.isEmpty()) {
            System.out.println("Группы по данному запросу не найдены.");
        } else {
            for (Group group : groups) {
                System.out.println(group);
            }
        }
    }
}
