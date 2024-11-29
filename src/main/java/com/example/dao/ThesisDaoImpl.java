package com.example.dao;

import com.example.model.Thesis;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ThesisDaoImpl implements ThesisDao {

    private final SessionFactory sessionFactory;

    public ThesisDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Thesis getByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Thesis.class, gradeBook);
        }
    }

    @Override
    public List<Thesis> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Thesis", Thesis.class).list();
        }
    }

    @Override
    public void save(Thesis thesis) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(thesis);
            transaction.commit();
        }
    }

    @Override
    public void update(Thesis thesis) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(thesis);
            transaction.commit();
        }
    }

    @Override
    public void delete(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Thesis thesis = session.get(Thesis.class, gradeBook);
            if (thesis != null) {
                session.remove(thesis);
            }
            transaction.commit();
        }
    }

    @Override
    public List<Thesis> search(String query) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Thesis t where t.gradeBook like :query or t.teacherCode like :query or t.topic like :query";
            Query<Thesis> hQuery = session.createQuery(hql, Thesis.class);
            hQuery.setParameter("query", "%" + query + "%");
            return hQuery.list();
        }
    }
}
