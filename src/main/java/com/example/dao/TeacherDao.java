package com.example.dao;

import com.example.model.Teacher;

import java.util.List;

public interface TeacherDao {

    Teacher getByTeacherCode(String teacherCode);

    List<Teacher> getAll();

    void save(Teacher teacher);

    void update(Teacher teacher);

    void delete(String teacherCode);

    List<Teacher> search(String query);
}
