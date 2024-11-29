package com.example.dao;

import com.example.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GroupDaoImpl implements GroupDao {
    private final SessionFactory sessionFactory;

    public GroupDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Group getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Group.class, id);
        }
    }

    @Override
    public List<Group> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Group> query = session.createQuery("from Group", Group.class);
            return query.getResultList();
        }
    }

    @Override
    public void save(Group group) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(group);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Group group) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(group);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Group group = session.get(Group.class, id);
            if (group != null) {
                session.remove(group);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> search(String query) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Group where groupName like :query";
            Query<Group> hqlQuery = session.createQuery(hql, Group.class);
            hqlQuery.setParameter("query", "%" + query + "%");
            return hqlQuery.getResultList();
        }
    }
}
