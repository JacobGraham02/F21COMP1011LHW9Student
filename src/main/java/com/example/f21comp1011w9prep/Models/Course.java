package com.example.f21comp1011w9prep.Models;

public class Course {
    private String courseCode, courseName;

    public Course(String courseCode, String courseName) {
        setCourseCode(courseCode);
        setCourseName(courseName);
    }

    public String getCourseCode() {
        return courseCode;
    }

    /**
     * This method ensures the course code will match the pattern
     * XXXX 1234  where XXXX are any uppercase letters and 1234 are 4
     * digits in the range 0-9
     * @param courseCode
     */
    public void setCourseCode(String courseCode) {
        if (courseCode.matches("[A-Z]{4}\\s[0-9]{4}"))
            this.courseCode = courseCode;
        else
            throw new IllegalArgumentException("Course code must be in the form XXXX 1234");
    }

    public String getCourseName() {
        return courseName;
    }

    /**
     * This validates that the course name is 1-100 characters
     * in length
     * @param courseName
     */
    public void setCourseName(String courseName) {
        if (courseName.length()>0 && courseName.length()<=100)
            this.courseName = courseName;
        else
            throw new IllegalArgumentException("course name must be 1-100 characters");
    }

    public String toString()
    {
        return courseCode;
    }
}
