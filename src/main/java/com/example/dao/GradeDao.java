package com.example.dao;

import com.example.model.Grade;

import java.util.List;

public interface GradeDao {

    Grade getByGradeBook(String gradeBook);

    List<Grade> getAll();

    void save(Grade grade);

    void update(Grade grade);

    void delete(String gradeBook);

    List<Grade> search(String query);
}
