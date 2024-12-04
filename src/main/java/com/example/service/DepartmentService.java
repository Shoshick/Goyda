package com.example.service;

import com.example.dao.DepartmentDao;
import com.example.dao.DepartmentDaoImpl;
import com.example.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DepartmentService {
    private final DepartmentDao departmentDao;
    private final SessionFactory sessionFactory;

    public DepartmentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.departmentDao = new DepartmentDaoImpl(sessionFactory);
    }

    // Получение всех департаментов
    public List<Department> getAllDepartments() {
        try (Session session = sessionFactory.openSession()) {
            return departmentDao.getAll();
        }
    }

    // Получение департамента по ID
    public Department getDepartmentById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return departmentDao.getById(id);
        }
    }

    // Добавление нового департамента
    public void addDepartment(Department department) {
        try (Session session = sessionFactory.openSession()) {
            departmentDao.save(department);
        }
    }

    // Обновление департамента
    public void updateDepartment(Department department) {
        try (Session session = sessionFactory.openSession()) {
            departmentDao.update(department);
        }
    }

    // Удаление департамента
    public void deleteDepartment(Long id) {
        try {
            departmentDao.delete(id); // Вызов метода Dao
        } catch (RuntimeException e) {
            // Переименовываем сообщение для пользователя, если нужно
            if (e.getMessage().contains("департамент используется")) {
                throw new RuntimeException("Удаление невозможно: данный департамент связан с другими записями.");
            }
            throw new RuntimeException("Ошибка при удалении департамента.", e);
        }
    }
    
    

    // Поиск департаментов по запросу
    public List<Department> searchDepartments(String query) {
        try (Session session = sessionFactory.openSession()) {
            return departmentDao.search(query);
        }
    }
}
