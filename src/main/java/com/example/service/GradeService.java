package com.example.service;

import com.example.dao.GradeDao;
import com.example.dao.GradeDaoImpl;
import com.example.dao.StudentDao;
import com.example.dao.StudentDaoImpl; 
import com.example.model.Grade;
import com.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GradeService {

    private final GradeDao gradeDao;
    private final StudentDao studentDao; 
    private final SessionFactory sessionFactory;

    
    public GradeService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.gradeDao = new GradeDaoImpl(sessionFactory);
        this.studentDao = new StudentDaoImpl(sessionFactory); 
    }

    
    public Grade getGradeByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return gradeDao.getByGradeBook(gradeBook);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении оценки с зачетной книжки: " + gradeBook, e);
        }
    }

    
    public List<Grade> getAllGrades() {
        try (Session session = sessionFactory.openSession()) {
            return gradeDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка оценок", e);
        }
    }

    
    public void addGrade(Grade grade) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            gradeDao.save(grade);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при добавлении оценки: " + grade, e);
        }
    }

    
    public void updateGrade(Grade grade) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            gradeDao.update(grade);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при обновлении оценки: " + grade, e);
        }
    }

    
    public Student getStudentByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.getStudentByGradeBook(gradeBook); 
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении студента с зачетной книжки: " + gradeBook, e);
        }
    }

    
    public void deleteGrade(String gradeBook) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            gradeDao.delete(gradeBook);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении оценки с зачетной книжки: " + gradeBook, e);
        }
    }

    
    public List<Grade> searchGrades(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            return gradeDao.search(searchTerm);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске оценок по запросу: " + searchTerm, e);
        }
    }
}
