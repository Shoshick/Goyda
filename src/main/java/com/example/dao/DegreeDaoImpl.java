package com.example.dao;

import com.example.model.Degree;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DegreeDaoImpl implements DegreeDao {

    private final SessionFactory sessionFactory;

    public DegreeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Degree degree) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(degree);  // merge для отсоединённых сущностей
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка при сохранении степени", e);
            }
        }
    }

    @Override
    public Degree getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Degree.class, id);
        }
    }

    @Override
    public void update(Degree degree) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(degree);  // merge для обновления сущности
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка при обновлении степени", e);
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Degree degree = session.get(Degree.class, id);
                if (degree != null) {
                    session.remove(degree);  // remove для удаления сущности
                    transaction.commit();
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Ошибка при удалении степени", e);
            }
        }
    }

    @Override
    public List<Degree> search(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Degree d WHERE d.degree LIKE :searchTerm";
            Query<Degree> query = session.createQuery(hql, Degree.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            return query.list();
        }
    }

    @Override
    public List<Degree> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Degree";
            Query<Degree> query = session.createQuery(hql, Degree.class);
            return query.list();
        }
    }
}
