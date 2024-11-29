package com.example.dao;

import com.example.model.Student;

import java.util.List;

public interface StudentDao {

    Student getByGradeBook(String gradeBook);

    List<Student> getAll();

    void save(Student student);

    void update(Student student);

    void delete(String gradeBook);
    
    Student getStudentByGradeBook(String gradeBook);

    List<Student> search(String query);
}
