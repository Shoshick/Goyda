package com.example.dao;

import com.example.model.Rank;

import java.util.List;

public interface RankDao {

    Rank getById(Long id);

    List<Rank> getAll();

    void save(Rank rank);

    void update(Rank rank);

    void delete(Long id);

    List<Rank> search(String query);
}
