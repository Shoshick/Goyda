package com.example.service;

import com.example.dao.GroupDao;
import com.example.dao.GroupDaoImpl;
import com.example.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class GroupService {
    private final GroupDao groupDao;
    private final SessionFactory sessionFactory;

    // Конструктор, принимающий SessionFactory для использования в DAO
    public GroupService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.groupDao = new GroupDaoImpl(sessionFactory);  // Создаем GroupDao с использованием SessionFactory
    }

    // Получение всех групп
    public List<Group> getAllGroups() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Group> groups = groupDao.getAll();  // Вызов метода из DAO
            session.getTransaction().commit();
            return groups;
        }
    }

    // Получение группы по ID
    public Group getGroupById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Group group = groupDao.getById(id);
            session.getTransaction().commit();
            return group;
        }
    }

    // Добавление новой группы
    public void addGroup(Group group) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            groupDao.save(group);  // Вызов метода из DAO
            session.getTransaction().commit();
        }
    }

    // Обновление группы
    public void updateGroup(Group group) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            groupDao.update(group);  // Вызов метода из DAO
            session.getTransaction().commit();
        }
    }

    // Удаление группы
    public void deleteGroup(Long id) {
        try {
            groupDao.delete(id); // Вызов метода из DAO
        } catch (RuntimeException e) {
            if (e.getMessage().contains("используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данная группа связана с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении группы.", e);
        }
    }
    

    // Поиск групп по запросу
    public List<Group> searchGroups(String query) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Group> groups = groupDao.search(query);  // Вызов метода из DAO
            session.getTransaction().commit();
            return groups;
        }
    }
}
