package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "theses")
public class Thesis {

    @Id
    @Column(name = "grade_book", length = 10, nullable = false, unique = true)
    private String gradeBook;

    @OneToOne
    @JoinColumn(name = "grade_book", referencedColumnName = "grade_book", nullable = false, insertable = false, updatable = false)
    private Student student;

    @Column(name = "teacher_code", length = 10, nullable = false)
    private String teacherCode;

    @ManyToOne
    @JoinColumn(name = "teacher_code", referencedColumnName = "teacher_code", nullable = false, insertable = false, updatable = false)
    private Teacher teacher;

    @Column(name = "topic", length = 255, nullable = false)
    private String topic;

    
    public String getGradeBook() {
        return gradeBook;
    }

    public void setGradeBook(String gradeBook) {
        this.gradeBook = gradeBook;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Thesis{" +
                "gradeBook='" + gradeBook + '\'' +
                ", student=" + student.getFullName() +
                ", teacher=" + teacher.getFullName() +
                ", topic='" + topic + '\'' +
                '}';
    }
}
