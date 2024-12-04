package com.example.service;

import com.example.dao.TeacherDao;
import com.example.dao.TeacherDaoImpl;
import com.example.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TeacherService {

    private final TeacherDao teacherDao;
    private final SessionFactory sessionFactory;

    public TeacherService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.teacherDao = new TeacherDaoImpl(sessionFactory);
    }

    public Teacher getTeacherByCode(String teacherCode) {
        try (Session session = sessionFactory.openSession()) {
            return teacherDao.getByTeacherCode(teacherCode);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении учителя с кодом: " + teacherCode, e);
        }
    }

    public List<Teacher> getAllTeachers() {
        try (Session session = sessionFactory.openSession()) {
            return teacherDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка учителей", e);
        }
    }

    public void addTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            teacherDao.save(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при добавлении учителя: " + teacher, e);
        }
    }

    public void updateTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            teacherDao.update(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при обновлении учителя: " + teacher, e);
        }
    }

    public void deleteTeacher(String teacherCode) {
        try {
            teacherDao.delete(teacherCode);  // Вызов метода из DAO
        } catch (RuntimeException e) {
            if (e.getMessage().contains("учитель используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данный учитель связан с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении учителя.", e);
        }
    }
    

    public List<Teacher> searchTeachers(String query) {
        try (Session session = sessionFactory.openSession()) {
            return teacherDao.search(query);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске учителей по запросу: " + query, e);
        }
    }
    
}
