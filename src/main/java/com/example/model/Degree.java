package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "degrees")
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degree_id")  // Обновлено имя столбца на degree_id
    private Long degreeId;  // Переименовано поле на degreeId

    @Column(nullable = false, unique = true)
    private String degree;

    // Конструктор без параметров (требуется Hibernate)
    public Degree() {
    }

    // Новый конструктор с Long
    public Degree(Long degreeId) {
        this.degreeId = degreeId;
    }

    // Геттеры и сеттеры
    public Long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Long degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Degree{degreeId=" + degreeId + ", degree='" + degree + "'}";
    }
}
