package com.example.test;

import com.example.model.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DepartmentTest {

    public static void main(String[] args) {
        // Настройка Hibernate и создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Department.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // Добавление записи
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Department newDepartment = new Department();
            newDepartment.setDepartment("Physics Department");
            session.persist(newDepartment);
            transaction.commit();
            System.out.println("Added Department: " + newDepartment);

            // Поиск записи (все поля, включая частичное совпадение)
            session = sessionFactory.openSession();
            Query<Department> searchQuery = session.createQuery("FROM Department WHERE department LIKE :search", Department.class);
            searchQuery.setParameter("search", "%Physics%");
            List<Department> searchResults = searchQuery.getResultList();
            System.out.println("Search results for Department: " + searchResults);

            // Редактирование записи
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Department departmentToUpdate = searchResults.get(0); // Предполагается, что результат существует
            departmentToUpdate.setDepartment("Mathematics Department");
            session.merge(departmentToUpdate);
            transaction.commit();
            System.out.println("Updated Department: " + departmentToUpdate);

            // Проверка изменений
            session = sessionFactory.openSession();
            Query<Department> updatedQuery = session.createQuery("FROM Department WHERE department = :exactMatch", Department.class);
            updatedQuery.setParameter("exactMatch", "Mathematics Department");
            List<Department> updatedResults = updatedQuery.getResultList();
            System.out.println("Updated search results: " + updatedResults);

            // Удаление записей
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            MutationQuery deleteQuery = session.createMutationQuery("DELETE FROM Department WHERE department IN (:departmentsToDelete)");
            deleteQuery.setParameter("departmentsToDelete", List.of("Physics Department", "Mathematics Department"));
            int deletedRows = deleteQuery.executeUpdate();

            transaction.commit();
            System.out.println("Deleted rows: " + deletedRows);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            sessionFactory.close();
        }
    }
}
