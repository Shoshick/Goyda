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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Teacher teacher = session.get(Teacher.class, teacherCode);  
            if (teacher != null) {
                session.remove(teacher);  
            } else {
                throw new RuntimeException("Учитель с данным кодом не найден.");
            }
            transaction.commit();  
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Удаление невозможно: учитель используется в других записях.", e);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении учителя.", e);
        }
    }


@Override
public List<Teacher> search(String query) {
    try (Session session = sessionFactory.openSession()) {
        String hql = "FROM Teacher t WHERE t.fullName LIKE :query OR t.teacherCode LIKE :query OR CAST(t.rank.rankId AS string) LIKE :query OR CAST(t.degree.degreeId AS string) LIKE :query OR CAST(t.department.departmentId AS string) LIKE :query OR t.phone LIKE :query or t.email LIKE :query";
        return session.createQuery(hql, Teacher.class)
                      .setParameter("query", "%" + query + "%")
                      .list();
    } catch (Exception e) {
        throw new RuntimeException("Ошибка при поиске учителей", e);
    }
}

}
