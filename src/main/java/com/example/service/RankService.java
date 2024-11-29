package com.example.service;

import com.example.dao.RankDao;
import com.example.dao.RankDaoImpl;
import com.example.model.Rank;
import org.hibernate.SessionFactory;

import java.util.List;

public class RankService {
    private final RankDao rankDao;

    public RankService(SessionFactory sessionFactory) {
        this.rankDao = new RankDaoImpl(sessionFactory);
    }

    // Получение всех рангов
    public List<Rank> getAllRanks() {
        return rankDao.getAll();
    }

    // Получение ранга по ID
    public Rank getRankById(Long id) {
        return rankDao.getById(id);
    }

    // Добавление нового ранга
    public void addRank(Rank rank) {
        rankDao.save(rank);
    }

    // Обновление ранга
    public void updateRank(Rank rank) {
        rankDao.update(rank);
    }

    // Удаление ранга
    public void deleteRank(Long id) {
        rankDao.delete(id);
    }

    // Поиск рангов по запросу
    public List<Rank> searchRanks(String query) {
        return rankDao.search(query);
    }
}
