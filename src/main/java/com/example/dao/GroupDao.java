package com.example.dao;

import com.example.model.Group;

import java.util.List;

public interface GroupDao {

    Group getById(Long id);

    List<Group> getAll();

    void save(Group group);

    void update(Group group);

    void delete(Long id);

    List<Group> search(String query);
}