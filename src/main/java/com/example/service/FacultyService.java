package com.example.service;

import com.example.dao.FacultyDao;
import com.example.dao.FacultyDaoImpl;
import com.example.model.Faculty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class FacultyService {

    private final FacultyDao facultyDao;
    private final SessionFactory sessionFactory;

    // Конструктор, принимающий SessionFactory для использования в DAO
    public FacultyService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.facultyDao = new FacultyDaoImpl(sessionFactory);  // Создаем FacultyDao с использованием SessionFactory
    }

    // Получение всех факультетов
    public List<Faculty> getAllFaculties() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Faculty> faculties = facultyDao.getAll();  // Вызов метода из DAO
            session.getTransaction().commit();
            return faculties;
        }
    }

    // Получение факультета по ID
    public Faculty getFacultyById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Faculty faculty = facultyDao.getById(id);
            session.getTransaction().commit();
            return faculty;
        }
    }

    // Добавление нового факультета
    public void addFaculty(Faculty faculty) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            facultyDao.save(faculty);  // Вызов метода из DAO
            session.getTransaction().commit();
        }
    }

    // Обновление факультета
    public void updateFaculty(Faculty faculty) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            facultyDao.update(faculty);  // Вызов метода из DAO
            session.getTransaction().commit();
        }
    }

    // Удаление факультета
    public void deleteFaculty(Long id) {
        try {
            facultyDao.delete(id); // Вызов метода из DAO
        } catch (RuntimeException e) {
            if (e.getMessage().contains("используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данный факультет связан с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении факультета.", e);
        }
    }
    

    // Поиск факультетов по запросу
    public List<Faculty> searchFaculties(String query) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Faculty> faculties = facultyDao.search(query);  // Вызов метода из DAO
            session.getTransaction().commit();
            return faculties;
        }
    }
}
