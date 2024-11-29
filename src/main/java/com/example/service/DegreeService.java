package com.example.service;

import com.example.dao.DegreeDao;
import com.example.dao.DegreeDaoImpl;
import com.example.model.Degree;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DegreeService {
    private final DegreeDao degreeDao;
    private final SessionFactory sessionFactory;

    public DegreeService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.degreeDao = new DegreeDaoImpl(sessionFactory);  // DAO создаётся с SessionFactory
    }

    // Получение всех степеней
    public List<Degree> getAllDegrees() {
        try (Session session = sessionFactory.openSession()) {
            return degreeDao.getAll();  // Вызов DAO для получения всех степеней
        }
    }

    // Получение степени по ID
    public Degree getDegreeById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return degreeDao.getById(id);  // Вызов DAO для получения степени по ID
        }
    }

    // Добавление новой степени
    public void addDegree(Degree degree) {
        try (Session session = sessionFactory.openSession()) {
            degreeDao.save(degree);  // Вызов DAO для добавления степени
        }
    }

    // Обновление степени
    public void updateDegree(Degree degree) {
        try (Session session = sessionFactory.openSession()) {
            degreeDao.update(degree);  // Вызов DAO для обновления степени
        }
    }

    // Удаление степени
    public void deleteDegree(Long id) {
        try (Session session = sessionFactory.openSession()) {
            degreeDao.delete(id);  // Вызов DAO для удаления степени
        }
    }

    // Поиск степеней по запросу
    public List<Degree> searchDegrees(String query) {
        try (Session session = sessionFactory.openSession()) {
            return degreeDao.search(query);  // Вызов DAO для поиска степеней
        }
    }
}
