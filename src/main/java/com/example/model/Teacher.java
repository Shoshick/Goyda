package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name = "teacher_code", length = 10, nullable = false, unique = true)
    private String teacherCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;

    @ManyToOne
    @JoinColumn(name = "rank_id", nullable = false)
    private Rank rank;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherCode='" + teacherCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", degree=" + degree.getDegree() +
                ", rank=" + rank.getRank() +
                ", department=" + department.getDepartment() +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
