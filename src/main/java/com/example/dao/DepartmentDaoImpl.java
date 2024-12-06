package com.example.dao;

import com.example.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {

    private final SessionFactory sessionFactory;

    public DepartmentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Department department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(department); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при сохранении департамента", e);
        }
    }

    @Override
    public Department getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Department.class, id); 
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении департамента по ID", e);
        }
    }

    @Override
    public void update(Department department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(department); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при обновлении департамента", e);
        }
    }

@Override
public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        Department department = session.get(Department.class, id);
        if (department != null) {
            session.remove(department); 
        }
        transaction.commit();
    } catch (org.hibernate.exception.ConstraintViolationException e) {
        if (transaction != null) {
            transaction.rollback();
        }
        throw new RuntimeException("Удаление невозможно: департамент используется в других записях.", e);
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        throw new RuntimeException("Ошибка при удалении департамента.", e);
    }
}



    @Override
    public List<Department> search(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Department d WHERE d.department LIKE :searchTerm";
            Query<Department> query = session.createQuery(hql, Department.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%"); 
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске департаментов", e);
        }
    }

    @Override
    public List<Department> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Department";
            Query<Department> query = session.createQuery(hql, Department.class);
            return query.list(); 
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении всех департаментов", e);
        }
    }
}
