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
        this.degreeDao = new DegreeDaoImpl(sessionFactory);  
    }

    
    public List<Degree> getAllDegrees() {
        try (Session session = sessionFactory.openSession()) {
            return degreeDao.getAll();  
        }
    }

    
    public Degree getDegreeById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return degreeDao.getById(id);  
        }
    }

    
    public void addDegree(Degree degree) {
        try (Session session = sessionFactory.openSession()) {
            degreeDao.save(degree);  
        }
    }

    
    public void updateDegree(Degree degree) {
        try (Session session = sessionFactory.openSession()) {
            degreeDao.update(degree);  
        }
    }

    
    public void deleteDegree(Long id) {
        try {
            degreeDao.delete(id); // Вызов метода Dao
        } catch (RuntimeException e) {
            // Переименовываем сообщение для пользователя, если нужно
            if (e.getMessage().contains("степень используется")) {
                throw new RuntimeException("Удаление невозможно: данная степень связана с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении степени.", e);
        }
    }
    
    
    

    
    
    

    public List<Degree> searchDegrees(String query) {
        try (Session session = sessionFactory.openSession()) {
            return degreeDao.search(query);  
        }
    }
}
