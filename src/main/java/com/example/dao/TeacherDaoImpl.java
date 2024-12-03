package com.example.dao;


import com.example.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TeacherDaoImpl implements TeacherDao {

    private final SessionFactory sessionFactory;

    public TeacherDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Teacher getByTeacherCode(String teacherCode) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Teacher.class, teacherCode);
        }
    }

    @Override
    public List<Teacher> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Teacher", Teacher.class).list();
        }
    }

    @Override
    public void save(Teacher teacher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(teacher);
            transaction.commit();
        }
    }

    @Override
    public void update(Teacher teacher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(teacher);
            transaction.commit();
        }
    }

    @Override
    public void delete(String teacherCode) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Teacher teacher = session.get(Teacher.class, teacherCode);
            if (teacher != null) {
                session.remove(teacher);
            }
            transaction.commit();
        }
    }

    @Override
public List<Teacher> search(String query) {
    try (Session session = sessionFactory.openSession()) {
        String hql = "FROM Teacher WHERE fullName LIKE :query OR teacherCode LIKE :query";
        return session.createQuery(hql, Teacher.class)
                      .setParameter("query", "%" + query + "%")
                      .list();
    } catch (Exception e) {
        throw new RuntimeException("Ошибка при поиске учителей", e);
    }
}

}
