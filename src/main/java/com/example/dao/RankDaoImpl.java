package com.example.dao;

import com.example.model.Rank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RankDaoImpl implements RankDao {

    private final SessionFactory sessionFactory;

    public RankDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Rank getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Rank.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении ранга по ID", e);
        }
    }

    @Override
    public List<Rank> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Rank";
            Query<Rank> query = session.createQuery(hql, Rank.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении всех рангов", e);
        }
    }

    @Override
    public void save(Rank rank) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(rank);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при сохранении ранга", e);
        }
    }

    @Override
    public void update(Rank rank) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(rank);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при обновлении ранга", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Rank rank = session.get(Rank.class, id);
            if (rank != null) {
                session.remove(rank);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при удалении ранга", e);
        }
    }

    @Override
    public List<Rank> search(String query) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Rank r WHERE lower(r.name) LIKE :query";
            Query<Rank> searchQuery = session.createQuery(hql, Rank.class);
            searchQuery.setParameter("query", "%" + query.toLowerCase() + "%");
            return searchQuery.list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске рангов", e);
        }
    }
}
