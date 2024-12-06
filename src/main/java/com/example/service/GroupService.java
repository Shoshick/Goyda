package com.example.service;

import com.example.dao.GroupDao;
import com.example.dao.GroupDaoImpl;
import com.example.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class GroupService {
    private final GroupDao groupDao;
    private final SessionFactory sessionFactory;

    
    public GroupService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.groupDao = new GroupDaoImpl(sessionFactory);  
    }

    
    public List<Group> getAllGroups() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Group> groups = groupDao.getAll();  
            session.getTransaction().commit();
            return groups;
        }
    }

    
    public Group getGroupById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Group group = groupDao.getById(id);
            session.getTransaction().commit();
            return group;
        }
    }

    
    public void addGroup(Group group) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            groupDao.save(group);  
            session.getTransaction().commit();
        }
    }

    
    public void updateGroup(Group group) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            groupDao.update(group);  
            session.getTransaction().commit();
        }
    }

    
    public void deleteGroup(Long id) {
        try {
            groupDao.delete(id); 
        } catch (RuntimeException e) {
            if (e.getMessage().contains("используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данная группа связана с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении группы.", e);
        }
    }
    

    
    public List<Group> searchGroups(String query) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Group> groups = groupDao.search(query);  
            session.getTransaction().commit();
            return groups;
        }
    }
}
