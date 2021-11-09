package com.example.f21comp1011w9prep.Utilities;

import com.example.f21comp1011w9prep.Models.Course;
import com.example.f21comp1011w9prep.Models.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class MagicData {

    /**
     * This method will return an ArrayList of Student objects
     * @return ArrayList<Student></Student>
     */
    public static ArrayList<Student> getStudents()  {
        ArrayList<Student> students = new ArrayList<>();

        try {
            //load the names from the CSV file to create random students
            File file = new File("randomNames.csv");
            Scanner scanner = new Scanner(file);
            int studentNum = 10001;
            while (scanner.hasNext()) {
                String[] fullName = scanner.nextLine().split(" ");
                students.add(new Student(studentNum++, fullName[0], fullName[1]));
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        assignRandomGrades(students);
        return students;
    }

    /**
     * This method will return a HashSet of course codes
     * @return HashSet<String>
     */
    public static ArrayList<Course> getCourseCodes()
    {
        ArrayList<Course> courseCodes = new ArrayList<>();

        //Read the courses.txt file to get the course codes
        try {
            File file = new File("courses.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext())
            {
                String[] courseText = scanner.nextLine().split("-");
                courseCodes.add(new Course(courseText[0].trim(),
                        courseText[1].trim()));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return courseCodes;
    }

    /**
     * This method recieves an ArrayList of Student objects
     * and adds random grades to each student.  Each student will get
     * 3 random courses with random grades in the range of 45-100
     */
    public static void assignRandomGrades(ArrayList<Student> students)
    {
        SecureRandom rng = new SecureRandom();
        ArrayList<Course> courses = MagicData.getCourseCodes();

        //loop over every student
        for (Student student: students)
        {
            //add 3 random courses
            for (int i=1; i<=rng.nextInt(25); i++)
            {
                student.addCourse(courses.get(rng.nextInt(courses.size())),rng.nextInt(55)+45);
            }
        }
    }
}
