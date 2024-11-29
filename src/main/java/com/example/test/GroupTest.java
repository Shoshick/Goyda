package com.example.test;

import com.example.model.Group;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class GroupTest {

    public static void main(String[] args) {
        // Настройка Hibernate и создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Group.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // Добавление записи
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Group newGroup = new Group();
            newGroup.setGroupName("CS-101");
            session.persist(newGroup);
            transaction.commit();
            System.out.println("Added Group: " + newGroup);

            // Поиск записи (все поля, включая частичное совпадение)
            session = sessionFactory.openSession();
            Query<Group> searchQuery = session.createQuery("FROM Group WHERE groupName LIKE :search", Group.class);
            searchQuery.setParameter("search", "%CS%");
            List<Group> searchResults = searchQuery.getResultList();
            System.out.println("Search results for Group: " + searchResults);

            // Редактирование записи
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Group groupToUpdate = searchResults.get(0); // Предполагается, что результат существует
            groupToUpdate.setGroupName("CS-102");
            session.merge(groupToUpdate);
            transaction.commit();
            System.out.println("Updated Group: " + groupToUpdate);

            // Проверка изменений
            session = sessionFactory.openSession();
            Query<Group> updatedQuery = session.createQuery("FROM Group WHERE groupName = :exactMatch", Group.class);
            updatedQuery.setParameter("exactMatch", "CS-102");
            List<Group> updatedResults = updatedQuery.getResultList();
            System.out.println("Updated search results: " + updatedResults);

            // Удаление записей
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            MutationQuery deleteQuery = session.createMutationQuery("DELETE FROM Group WHERE groupName IN (:groupsToDelete)");
            deleteQuery.setParameter("groupsToDelete", List.of("CS-101", "CS-102"));
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
