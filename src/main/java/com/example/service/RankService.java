package com.example.service;

import com.example.dao.RankDao;
import com.example.dao.RankDaoImpl;
import com.example.model.Rank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class RankService {

    private final RankDao rankDao;
    private final SessionFactory sessionFactory;

    public RankService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.rankDao = new RankDaoImpl(sessionFactory);
    }

    public List<Rank> getAllRanks() {
        try (Session session = sessionFactory.openSession()) {
            return rankDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка рангов", e);
        }
    }

    public Rank getRankById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return rankDao.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении ранга с ID: " + id, e);
        }
    }

    public void addRank(Rank rank) {
        try (Session session = sessionFactory.openSession()) {
            rankDao.save(rank);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при добавлении ранга: " + rank, e);
        }
    }

    public void updateRank(Rank rank) {
        try (Session session = sessionFactory.openSession()) {
            rankDao.update(rank);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении ранга: " + rank, e);
        }
    }

    public void deleteRank(Long id) {
        try {
            rankDao.delete(id);  
        } catch (RuntimeException e) {
            if (e.getMessage().contains("используется в других записях")) {
                throw new RuntimeException("Удаление невозможно: данный ранг связан с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении ранга.", e);
        }
    }
    
    

    public List<Rank> searchRanks(String query) {
        try (Session session = sessionFactory.openSession()) {
            return rankDao.search(query);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске рангов по запросу: " + query, e);
        }
    }
}
