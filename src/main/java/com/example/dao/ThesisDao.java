package com.example.dao;

import com.example.model.Thesis;

import java.util.List;

public interface ThesisDao {

    Thesis getByGradeBook(String gradeBook);

    List<Thesis> getAll();

    void save(Thesis thesis);

    void update(Thesis thesis);

    void delete(String gradeBook);

    List<Thesis> search(String query);
}
