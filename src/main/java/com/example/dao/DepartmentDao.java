package com.example.dao;

import com.example.model.Department;

import java.util.List;

public interface DepartmentDao {

    Department getById(Long id);

    List<Department> getAll();

    void save(Department department);

    void update(Department department);

    void delete(Long id);

    List<Department> search(String query);
}

