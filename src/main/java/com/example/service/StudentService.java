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

    // Конструктор, принимающий SessionFactory для использования в DAO
    public StudentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.studentDao = new StudentDaoImpl(sessionFactory);
    }

    // Получение студента по номеру зачетной книжки
    public Student getStudentByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.getByGradeBook(gradeBook);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении студента с зачетной книжкой: " + gradeBook, e);
        }
    }

    // Получение всех студентов
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка студентов", e);
        }
    }

    // Добавление нового студента
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

    // Обновление информации о студенте
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

    // Удаление студента по номеру зачетной книжки
    public void deleteStudent(String gradeBook) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            studentDao.delete(gradeBook);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении студента с зачетной книжкой: " + gradeBook, e);
        }
    }

    // Поиск студентов по строковому запросу
    public List<Student> searchStudents(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            return studentDao.search(searchTerm);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске студентов по запросу: " + searchTerm, e);
        }
    }
}
