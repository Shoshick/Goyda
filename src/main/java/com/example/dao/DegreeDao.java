package com.example.dao;

import com.example.model.Degree;

import java.util.List;

public interface DegreeDao {

    Degree getById(Long id);

    List<Degree> getAll();

    void save(Degree degree);

    void update(Degree degree);

    void delete(Long id);

    List<Degree> search(String query);
}
