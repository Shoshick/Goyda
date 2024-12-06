package com.example.ui;

import com.example.service.*;
import com.example.util.HibernateUtil;
import com.example.util.ConsoleUtils;




import org.hibernate.SessionFactory;
import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        
        RankService rankService = new RankService(sessionFactory);
        DegreeService degreeService = new DegreeService(sessionFactory);
        DepartmentService departmentService = new DepartmentService(sessionFactory);
        FacultyService facultyService = new FacultyService(sessionFactory);
        GroupService groupService = new GroupService(sessionFactory);
        StudentService studentService = new StudentService(sessionFactory);
        GradeService gradeService = new GradeService(sessionFactory);  
        TeacherService teacherService = new TeacherService(sessionFactory);  
        ThesisService thesisService = new ThesisService(sessionFactory);  

        
        while (running) {
            ConsoleUtils.clearScreen();
            System.out.println("Выберите таблицу для работы:");
            System.out.println("1. Rank");
            System.out.println("2. Degree");
            System.out.println("3. Department");
            System.out.println("4. Faculty");
            System.out.println("5. Group");
            System.out.println("6. Student");
            System.out.println("7. Grade");  
            System.out.println("8. Teacher"); 
            System.out.println("9. Thesis");  
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  

                switch (choice) {
                    case 1:
                        displayRankMenu(scanner, rankService);  
                        break;
                    case 2:
                        displayDegreeMenu(scanner, degreeService);  
                        break;
                    case 3:
                        displayDepartmentMenu(scanner, departmentService);  
                        break;
                    case 4:
                        displayFacultyMenu(scanner, facultyService);  
                        break;
                    case 5:
                        displayGroupMenu(scanner, groupService);  
                        break;
                    case 6:
                        displayStudentMenu(scanner, studentService);  
                        break;
                    case 7:
                        displayGradeMenu(scanner, gradeService);  
                        break;
                    case 8:
                        displayTeacherMenu(scanner, teacherService);  
                        break;
                    case 9:
                        displayThesisMenu(scanner, thesisService);  
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        running = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                        ConsoleUtils.waitForEnter(); 
                }
            }

        scanner.close(); 
        HibernateUtil.shutdown(); 
    }

    public static void displayRankMenu(Scanner scanner, RankService rankService) {
        RankUI rankUI = new RankUI(rankService);
        rankUI.showMenu();
    }

    public static void displayDegreeMenu(Scanner scanner, DegreeService degreeService) {
        DegreeUI degreeUI = new DegreeUI(degreeService);
        ConsoleUtils.clearScreen();
        degreeUI.showMenu();
    }

    public static void displayDepartmentMenu(Scanner scanner, DepartmentService departmentService) {
        DepartmentUI departmentUI = new DepartmentUI(departmentService);
        departmentUI.showMenu();
    }

    public static void displayFacultyMenu(Scanner scanner, FacultyService facultyService) {
        FacultyUI facultyUI = new FacultyUI(facultyService);
        facultyUI.showMenu();
    }

    public static void displayGroupMenu(Scanner scanner, GroupService groupService) {
        GroupUI groupUI = new GroupUI(groupService);
        groupUI.showMenu();
    }

    public static void displayStudentMenu(Scanner scanner, StudentService studentService) {
        StudentUI studentUI = new StudentUI(studentService);
        studentUI.showMenu();
    }

    public static void displayGradeMenu(Scanner scanner, GradeService gradeService) {
        GradeUI gradeUI = new GradeUI(gradeService);  
        gradeUI.showMenu();  
    }

    public static void displayTeacherMenu(Scanner scanner, TeacherService teacherService) {
        TeacherUI teacherUI = new TeacherUI(teacherService);
        teacherUI.showMenu();  
    }

    public static void displayThesisMenu(Scanner scanner, ThesisService thesisService) {
        ThesisUI thesisUI = new ThesisUI(thesisService);
        thesisUI.showMenu();  
    }
}
