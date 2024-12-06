package com.example.dao;

import com.example.model.Grade;
import com.example.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GradeDaoImpl implements GradeDao {

    private final SessionFactory sessionFactory;

    public GradeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Grade getByGradeBook(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Grade g where g.student.gradeBook = :gradeBook", Grade.class)
                    .setParameter("gradeBook", gradeBook)
                    .uniqueResult();
        }
    }

    @Override
    public List<Grade> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Grade", Grade.class).getResultList();
        }
    }

    @Override
    public void save(Grade grade) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(grade);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Grade grade) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(grade);  
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String gradeBook) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Grade grade = getByGradeBook(gradeBook);
            if (grade != null) {
                session.remove(grade);
            }
            session.getTransaction().commit();
        }
    }

    @Override
public List<Grade> search(String query) {
    try (Session session = sessionFactory.openSession()) {
        String hql = "FROM Grade g WHERE g.student.gradeBook LIKE :query OR CAST(g.examGrade AS string) LIKE :query OR CAST(g.diplomaGrade AS string) LIKE :query";
        Query<Grade> hQuery = session.createQuery(hql, Grade.class);
        hQuery.setParameter("query", "%" + query + "%");
        return hQuery.list();
    }
}

}
