package com.example.test;

import com.example.model.Faculty;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class FacultyTest {

    public static void main(String[] args) {
        // Настройка Hibernate и создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Faculty.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // Добавление записи
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Faculty newFaculty = new Faculty();
            newFaculty.setFaculty("Engineering Faculty");
            session.persist(newFaculty);
            transaction.commit();
            System.out.println("Added Faculty: " + newFaculty);

            // Поиск записи (все поля, включая частичное совпадение)
            session = sessionFactory.openSession();
            Query<Faculty> searchQuery = session.createQuery("FROM Faculty WHERE faculty LIKE :search", Faculty.class);
            searchQuery.setParameter("search", "%Engineering%");
            List<Faculty> searchResults = searchQuery.getResultList();
            System.out.println("Search results for Faculty: " + searchResults);

            // Редактирование записи
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Faculty facultyToUpdate = searchResults.get(0); // Предполагается, что результат существует
            facultyToUpdate.setFaculty("Science Faculty");
            session.merge(facultyToUpdate);
            transaction.commit();
            System.out.println("Updated Faculty: " + facultyToUpdate);

            // Проверка изменений
            session = sessionFactory.openSession();
            Query<Faculty> updatedQuery = session.createQuery("FROM Faculty WHERE faculty = :exactMatch", Faculty.class);
            updatedQuery.setParameter("exactMatch", "Science Faculty");
            List<Faculty> updatedResults = updatedQuery.getResultList();
            System.out.println("Updated search results: " + updatedResults);

            // Удаление записей
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            MutationQuery deleteQuery = session.createMutationQuery("DELETE FROM Faculty WHERE faculty IN (:facultiesToDelete)");
            deleteQuery.setParameter("facultiesToDelete", List.of("Engineering Faculty", "Science Faculty"));
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
