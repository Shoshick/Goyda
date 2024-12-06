package com.example.dao;

import com.example.model.Faculty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FacultyDaoImpl implements FacultyDao {

    private final SessionFactory sessionFactory;

    public FacultyDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Faculty faculty) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(faculty);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Faculty getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Faculty.class, id);
        }
    }

    @Override
    public void update(Faculty faculty) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(faculty);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Faculty faculty = session.get(Faculty.class, id);
            if (faculty != null) {
                session.remove(faculty); 
            } else {
                throw new RuntimeException("Факультет с данным ID не найден.");
            }
            transaction.commit(); 
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Удаление невозможно: факультет используется в других записях.", e);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при удалении факультета.", e);
        }
    }


    @Override
    public List<Faculty> search(String query) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Faculty f WHERE f.faculty LIKE :searchTerm";
            Query<Faculty> searchQuery = session.createQuery(hql, Faculty.class);
            searchQuery.setParameter("searchTerm", "%" + query + "%");
            return searchQuery.list();
        }
    }

    @Override
    public List<Faculty> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Faculty";
            Query<Faculty> query = session.createQuery(hql, Faculty.class);
            return query.list();
        }
    }
}
