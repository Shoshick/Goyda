package com.example.dao;

import com.example.model.Faculty;

import java.util.List;

public interface FacultyDao {

    Faculty getById(Long id);

    List<Faculty> getAll();

    void save(Faculty faculty);

    void update(Faculty faculty);

    void delete(Long id);

    List<Faculty> search(String query);
}
