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
            return session.get(Student.class, gradeBook);  
        }
    }

    @Override
    public Student getStudentByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student WHERE gradeBook = :gradeBook";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("gradeBook", gradeBook);
            return query.uniqueResult(); 
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
            session.merge(student);  
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String gradeBook) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, gradeBook);  
            if (student != null) {
                session.remove(student);  
            } else {
                throw new RuntimeException("Студент с данным номером зачетной книжки не найден.");
            }
            transaction.commit();  
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Удаление невозможно: студент используется в других записях.", e);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении студента.", e);
        }
    }

    @Override
    public List<Student> search(String query) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student s WHERE s.gradeBook LIKE :query OR s.fullName LIKE :query OR s.faculty.faculty LIKE :query OR s.group.groupName LIKE :query";
            Query<Student> hQuery = session.createQuery(hql, Student.class);
            hQuery.setParameter("query", "%" + query + "%");
            return hQuery.list();
        }
    }
}
