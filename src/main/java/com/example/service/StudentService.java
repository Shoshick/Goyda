package com.example.service;

import com.example.dao.StudentDao;
import com.example.dao.StudentDaoImpl;
import com.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StudentService {

    private final StudentDao studentDao;
    private final SessionFactory sessionFactory;

    
    public StudentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.studentDao = new StudentDaoImpl(sessionFactory);
    }

    
    public Student getStudentByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.getByGradeBook(gradeBook);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении студента с зачетной книжкой: " + gradeBook, e);
        }
    }

    
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка студентов", e);
        }
    }

    
    public void addStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            studentDao.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при добавлении студента: " + student, e);
        }
    }

    
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            studentDao.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при обновлении студента: " + student, e);
        }
    }

    
    public void deleteStudent(String gradeBook) {
        try {
            studentDao.delete(gradeBook);  
        } catch (RuntimeException e) {
            if (e.getMessage().contains("студент используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данный студент связан с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении студента.", e);
        }
    }
    

    
    public List<Student> searchStudents(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.search(searchTerm);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске студентов по запросу: " + searchTerm, e);
        }
    }
}
