package com.example.test;

import com.example.model.Rank;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class RankTest {

    public static void main(String[] args) {
        // Настройка Hibernate и создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Rank.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // Добавление записи
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Rank newRank = new Rank();
            newRank.setRank("Assistant Professor");
            session.persist(newRank);
            transaction.commit();
            System.out.println("Added Rank: " + newRank);

            // Поиск записи (все поля, включая частичное совпадение)
            session = sessionFactory.openSession();
            Query<Rank> searchQuery = session.createQuery("FROM Rank WHERE rank LIKE :search", Rank.class);
            searchQuery.setParameter("search", "%Professor%");
            List<Rank> searchResults = searchQuery.getResultList();
            System.out.println("Search results for Rank: " + searchResults);

            // Редактирование записи
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Rank rankToUpdate = searchResults.get(0); // Предполагается, что результат существует
            rankToUpdate.setRank("Associate Professor");
            session.merge(rankToUpdate);
            transaction.commit();
            System.out.println("Updated Rank: " + rankToUpdate);

            // Проверка изменений
            session = sessionFactory.openSession();
            Query<Rank> updatedQuery = session.createQuery("FROM Rank WHERE rank = :exactMatch", Rank.class);
            updatedQuery.setParameter("exactMatch", "Associate Professor");
            List<Rank> updatedResults = updatedQuery.getResultList();
            System.out.println("Updated search results: " + updatedResults);

            // Удаление записей
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            MutationQuery deleteQuery = session.createMutationQuery("DELETE FROM Rank WHERE rank IN (:ranksToDelete)");
            deleteQuery.setParameter("ranksToDelete", List.of("Assistant Professor", "Associate Professor"));
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
