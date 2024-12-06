package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")  
    private Long departmentId;

    @Column(nullable = false, unique = true)
    private String department;

    
    public Department() {
    }

    
    public Department(Long departmentId) {
        this.departmentId = departmentId;
    }

    
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Department{departmentId=" + departmentId + ", department='" + department + "'}";
    }
}
