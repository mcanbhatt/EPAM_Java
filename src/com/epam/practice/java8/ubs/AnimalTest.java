package com.epam.practice.java8.ubs;

import java.util.HashMap;
import java.util.Map;

public class AnimalTest {
	public static void main(String[] args) throws Exception {
		Animal animal = new Dog();
		animal.print(); // Output: "Dog"
		
		
		String str = "ABC";
		String b = "ABC";

		System.out.println(str == b);
		System.out.println(str.equals(b));


		/// What will be the output of below code?
		Map<Employee, Integer> map = new HashMap<>();

		Employee e1 = new Employee("X", "Y");
		Employee e2 = new Employee("X", "Y");

		map.put(e1, 100);
		map.put(e2, 200);

	   System.out.println(map.get(e1));
	   
	   //Exception in thread "main" java.lang.StackOverflowError
	       //A a = new A();
		
		//What will be the output?
		try {
		    // some code that throws NullPointerException
			throw new NullPointerException();
		} catch (NullPointerException npe) {
		    System.out.println(1);
		    throw new Exception();
		} catch (Exception e) {
		    System.out.println(2);
		} finally {
		    System.out.println(3);
		}
		
	}
	

}


class A {

    public A() {
        new B();
    }
}

class B {

    public B() {
        new A();
    }
}


class Employee {
    String firstName;
    String lastName;
    Integer salary;

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}



class Animal{
    public static void print() {
        System.out.println("Animal");
    }
}

class Dog extends Animal{
    public static void print() {
        System.out.println("Dog");
    }
}
