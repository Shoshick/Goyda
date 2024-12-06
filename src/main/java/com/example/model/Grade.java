package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @ManyToOne
    @JoinColumn(name = "grade_book", referencedColumnName = "grade_book")
    private Student student;

    @Column(name = "exam_grade")
    private Integer examGrade;

    @Column(name = "diploma_grade")
    private Integer diplomaGrade;

    

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(Integer examGrade) {
        this.examGrade = examGrade;
    }

    public Integer getDiplomaGrade() {
        return diplomaGrade;
    }

    public void setDiplomaGrade(Integer diplomaGrade) {
        this.diplomaGrade = diplomaGrade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "student=" + student.getGradeBook() +
                ", examGrade=" + examGrade +
                ", diplomaGrade=" + diplomaGrade +
                '}';
    }
}
