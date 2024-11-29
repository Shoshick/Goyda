package com.example.test;

import com.example.model.Degree;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DegreeTest {

    public static void main(String[] args) {
        // Настройка Hibernate и создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Degree.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // Добавление записи
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Degree newDegree = new Degree();
            newDegree.setDegree("Bachelor of Arts");
            session.persist(newDegree);
            transaction.commit();
            System.out.println("Added Degree: " + newDegree);

            // Поиск записи (все поля, включая частичное совпадение)
            session = sessionFactory.openSession();
            Query<Degree> searchQuery = session.createQuery("FROM Degree WHERE degree LIKE :search", Degree.class);
            searchQuery.setParameter("search", "%Bachelor%");
            List<Degree> searchResults = searchQuery.getResultList();
            System.out.println("Search results for Degree: " + searchResults);

            // Редактирование записи
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Degree degreeToUpdate = searchResults.get(0); // Предполагается, что результат существует
            degreeToUpdate.setDegree("Bachelor of Fine Arts");
            session.merge(degreeToUpdate);
            transaction.commit();
            System.out.println("Updated Degree: " + degreeToUpdate);

            // Проверка изменений
            session = sessionFactory.openSession();
            Query<Degree> updatedQuery = session.createQuery("FROM Degree WHERE degree = :exactMatch", Degree.class);
            updatedQuery.setParameter("exactMatch", "Bachelor of Fine Arts");
            List<Degree> updatedResults = updatedQuery.getResultList();
            System.out.println("Updated search results: " + updatedResults);

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            MutationQuery deleteQuery = session.createMutationQuery("DELETE FROM Degree WHERE degree IN (:degreesToDelete)");
            deleteQuery.setParameter("degreesToDelete", List.of("Bachelor of Arts", "Bachelor of Fine Arts"));
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
