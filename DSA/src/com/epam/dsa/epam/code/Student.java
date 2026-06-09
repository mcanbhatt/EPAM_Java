package com.epam.dsa.epam.code;
public class Student {
    private final String id;
    private final String grade;
    public Student(String id, String grade) {
        this.id = id;
        this.grade = grade;
    }
    public String getId() {
        return id;
    }
    public String getGrade() {
        return grade;
    }
    
    public String toString() {
    	return "id-> "+id +" grade-> "+grade;
    }
}
