package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "grade_book", unique = true)
    private String gradeBook;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "full_name")
    private String fullName;

    // Геттеры и сеттеры

    public String getGradeBook() {
        return gradeBook;
    }

    public void setGradeBook(String gradeBook) {
        this.gradeBook = gradeBook;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "gradeBook='" + gradeBook + '\'' +
                ", faculty=" + faculty.getFaculty() +  // Assuming Faculty has a getName method
                ", group=" + group.getGroupName() +      // Assuming Group has a getName method
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
