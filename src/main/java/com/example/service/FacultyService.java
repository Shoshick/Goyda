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

    
    public FacultyService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.facultyDao = new FacultyDaoImpl(sessionFactory);  
    }

    
    public List<Faculty> getAllFaculties() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Faculty> faculties = facultyDao.getAll();  
            session.getTransaction().commit();
            return faculties;
        }
    }

    
    public Faculty getFacultyById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Faculty faculty = facultyDao.getById(id);
            session.getTransaction().commit();
            return faculty;
        }
    }

    
    public void addFaculty(Faculty faculty) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            facultyDao.save(faculty);  
            session.getTransaction().commit();
        }
    }

    
    public void updateFaculty(Faculty faculty) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            facultyDao.update(faculty);  
            session.getTransaction().commit();
        }
    }

    
    public void deleteFaculty(Long id) {
        try {
            facultyDao.delete(id); 
        } catch (RuntimeException e) {
            if (e.getMessage().contains("используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данный факультет связан с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении факультета.", e);
        }
    }
    

    
    public List<Faculty> searchFaculties(String query) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Faculty> faculties = facultyDao.search(query);  
            session.getTransaction().commit();
            return faculties;
        }
    }
}
