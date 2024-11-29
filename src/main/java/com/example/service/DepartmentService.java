package com.example.service;

import com.example.dao.DepartmentDao;
import com.example.dao.DepartmentDaoImpl;
import com.example.model.Department;
import org.hibernate.SessionFactory;

import java.util.List;

public class DepartmentService {

    private final DepartmentDao departmentDao;

    public DepartmentService(SessionFactory sessionFactory) {
        this.departmentDao = new DepartmentDaoImpl(sessionFactory);
    }

    // Получение всех департаментов
    public List<Department> getAllDepartments() {
        return departmentDao.getAll();
    }

    // Получение департамента по ID
    public Department getDepartmentById(Long id) {
        return departmentDao.getById(id);
    }

    // Добавление нового департамента
    public void addDepartment(Department department) {
        departmentDao.save(department);
    }

    // Обновление департамента
    public void updateDepartment(Department department) {
        departmentDao.update(department);
    }

    // Удаление департамента
    public void deleteDepartment(Long id) {
        departmentDao.delete(id);
    }

    // Поиск департаментов по запросу
    public List<Department> searchDepartments(String query) {
        return departmentDao.search(query);
    }
}
