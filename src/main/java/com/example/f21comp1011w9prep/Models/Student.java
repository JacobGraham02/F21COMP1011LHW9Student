package com.example.f21comp1011w9prep.Models;

import com.example.f21comp1011w9prep.Models.Course;

import java.util.HashMap;
import java.util.Locale;

public class Student
{
    private String firstName, lastName;
    private int studNum;
    private HashMap<Course, Integer> courses;

    public Student(int studNum, String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        setStudNum(studNum);
        courses = new HashMap<>();
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * This method validates that the first name starts with an upper case letter
     * and only contains letters.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        if (firstName.matches("[A-Z][A-z]*[-]?[A-z]*"))
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("first name must start with upper case letter");
    }

    /**
     * returns the last name of the student
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method validates that the last name starts with an upper case letter and any
     * combination of letters, dashes or a space
     * @param lastName
     */
    public void setLastName(String lastName) {
        if (lastName.matches("[A-Z][A-z]*[-\\s]?[A-z]*"))
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("last name must start with an upper case letter");
    }

    public int getStudNum() {
        return studNum;
    }

    /**
     * This validates that the argument is greater than 0
     * @param studNum must be greater than 0
     */
    public void setStudNum(int studNum) {
        if (studNum>0)
            this.studNum = studNum;
        else
            throw new IllegalArgumentException("student number must be greater than 0");
    }

    /**
     * The HashMap that contains the courses and marks earned
     * @return keys are the course code, the value is the grade achieved
     */
    public HashMap<Course, Integer> getCourses() {
        return courses;
    }

    /**
     * This will add a course and grade to the DataStructures.Models.Student object
     * @param course -> must be a valid Course object
     * @param gradeAchieved -> must be in the range 0-100
     */
    public void addCourse(Course course, Integer gradeAchieved) {

        if (gradeAchieved<0 || gradeAchieved > 100)
            throw new IllegalArgumentException("grade must be in the range 0-100");

        courses.put(course,gradeAchieved);
    }

    /**
     * This method returns the average grade achieved for the student
     */
//    public double getAvgMark()
//    {
//        double sum = 0;
//        for (Integer grade : courses.values())
//            sum += grade;
//        return sum/courses.size();
//    }

    public double getAvgMark()
    {
        if (courses.size()==0)
            return 0;

        return courses.values().stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0.0);
    }

    public String getAvgGradeString()
    {
        return String.format("%.1f %%", getAvgMark());
    }

    public int getNumOfCourses()
    {
        return courses.size();
    }

    /**
     * returns the DataStructures.Models.Student as a String
     * @return student number-first name last name and avg grade
     */
    public String toString()
    {
        return String.format("%d-%s %s",studNum,firstName,lastName);
    }

    /**
     * This method returns true if the search criteria is found in the student number, first or last name
     */
    public boolean contains(String searchText)
    {
        searchText = searchText.toLowerCase();
        String stNum = Integer.toString(studNum);
        return firstName.toLowerCase().contains(searchText) || lastName.toLowerCase().contains(searchText)
                || stNum.contains(searchText);
    }



}
