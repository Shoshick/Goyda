package com.example.test;

import com.example.model.Faculty;
import com.example.model.Group;
import com.example.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentTest {

    public static void main(String[] args) {
        
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Faculty.class)
                .addAnnotatedClass(Group.class)
                .buildSessionFactory();

        Session session = null;

        try {
            
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Faculty faculty1 = new Faculty();
            faculty1.setFaculty("Engineering");
            session.persist(faculty1);

            Faculty faculty2 = new Faculty();
            faculty2.setFaculty("Science");
            session.persist(faculty2);

            Group group1 = new Group();
            group1.setGroupName("Group A");
            session.persist(group1);

            Group group2 = new Group();
            group2.setGroupName("Group B");
            session.persist(group2);

            transaction.commit();
            System.out.println("Added Faculties and Groups.");

            
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Student student1 = new Student();
            student1.setGradeBook("12345");
            student1.setFullName("John Doe");
            student1.setFaculty(faculty1); 
            student1.setGroup(group1); 
            session.merge(student1);
            transaction.commit();
            System.out.println("Added Student: " + student1);

            
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Student student2 = new Student();
            student2.setGradeBook("54321");
            student2.setFullName("Jane Doe");
            student2.setFaculty(faculty2); 
            student2.setGroup(new Group()); 
            session.merge(student2);
            transaction.commit();
            System.out.println("Added Student with non-existent group: " + student2);

            
            session = sessionFactory.openSession();
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            List<Student> students = query.getResultList();
            System.out.println("All Students: " + students);

            
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            MutationQuery deleteStudentQuery = session.createMutationQuery("DELETE FROM Student WHERE gradeBook IN (:gradeBooks)");
            deleteStudentQuery.setParameter("gradeBooks", List.of("12345", "54321"));
            int deletedStudents = deleteStudentQuery.executeUpdate();

            MutationQuery deleteFacultyQuery = session.createMutationQuery("DELETE FROM Faculty WHERE id IN (:ids)");
            deleteFacultyQuery.setParameter("ids", List.of(faculty1.getFacultyId(), faculty2.getFacultyId()));
            int deletedFaculties = deleteFacultyQuery.executeUpdate();

            MutationQuery deleteGroupQuery = session.createMutationQuery("DELETE FROM Group WHERE id IN (:ids)");
            deleteGroupQuery.setParameter("ids", List.of(group1.getGroupId(), group2.getGroupId()));
            int deletedGroups = deleteGroupQuery.executeUpdate();

            transaction.commit();
            System.out.println("Deleted Students: " + deletedStudents);
            System.out.println("Deleted Faculties: " + deletedFaculties);
            System.out.println("Deleted Groups: " + deletedGroups);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            sessionFactory.close();
        }
    }
}
