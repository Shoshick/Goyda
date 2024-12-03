package com.example.dao;

import com.example.model.Student;
import com.example.util.HibernateUtil;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;

import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private final SessionFactory sessionFactory;

    public StudentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Student getByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, gradeBook);  // Поиск по gradeBook
        }
    }

    @Override
    public Student getStudentByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student WHERE gradeBook = :gradeBook";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("gradeBook", gradeBook);
            return query.uniqueResult(); // Возвращает уникального студента по номеру зачетной книжки
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении студента по номеру зачетной книжки", e);
        }
    }

    @Override
    public List<Student> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student", Student.class).getResultList();
        }
    }

    @Override
    public void save(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Используем merge, чтобы поддерживать отсоединённые объекты
            session.merge(student);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(student);  // Используем merge вместо update
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.get(Student.class, gradeBook);  // Поиск студента по gradeBook
            if (student != null) {
                session.remove(student); // Используем remove вместо delete
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Student> search(String query) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "from Student s where s.gradeBook like :query or s.fullName like :query or s.faculty.name like :query or s.group.name like :query";
            Query<Student> hQuery = session.createQuery(queryString, Student.class);
            hQuery.setParameter("query", "%" + query + "%");
            return hQuery.getResultList();
        }
    }
}
