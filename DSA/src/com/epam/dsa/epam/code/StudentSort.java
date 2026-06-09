package com.epam.dsa.epam.code;

import java.util.Arrays;
import java.util.Comparator;

public class StudentSort {
	
	public static void main(String[] arg) {
		
		   Student[] students = {
	                new Student("3", "80"),
	                new Student("1", "90"),
	                new Student("2", "90"),
	                new Student("4",  "70"),
	                new Student("2", "50"),
	        };
		   
		   Student[] stds =   new StudentSort().sortStudentsByGradeAndId(students);
           for(Student std : stds){        	   
        	   System.out.println("--> "+std);
           }
		
	}
    /**
     * https://autocode-next.lab.epam.com/courses/1372/syllabus/5600
     *
     * @param students
     * @return
     */
    public Student[] sortStudentsByGradeAndId(Student[] students) {
    	
    	if(students ==null)
    		return null;
    	
    	return Arrays.stream(students).sorted(Comparator.comparing(Student::getGrade).thenComparing(
    			Comparator.comparing(Student::getId))).toArray(Student[]::new);
    	
    }
}
