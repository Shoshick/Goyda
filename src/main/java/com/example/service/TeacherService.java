package com.example.service;

import com.example.dao.TeacherDao;
import com.example.dao.TeacherDaoImpl;
import com.example.model.Teacher;
import org.hibernate.SessionFactory;

import java.util.List;

public class TeacherService {

    private final TeacherDao teacherDao;

    public TeacherService(SessionFactory sessionFactory) {
        this.teacherDao = new TeacherDaoImpl(sessionFactory);
    }

    public Teacher getTeacherByCode(String teacherCode) {
        return teacherDao.getByTeacherCode(teacherCode);
    }

    public List<Teacher> getAllTeachers() {
        return teacherDao.getAll();
    }

    public void addTeacher(Teacher teacher) {
        teacherDao.save(teacher);
    }

    public void updateTeacher(Teacher teacher) {
        teacherDao.update(teacher);
    }

    public void deleteTeacher(String teacherCode) {
        teacherDao.delete(teacherCode);
    }

    public List<Teacher> searchTeachers(String query) {
        return teacherDao.search(query);
    }
}
