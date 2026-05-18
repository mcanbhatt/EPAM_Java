package com.epam.practice.java17;

public record Person(String name, int age) {	
	
	 public Person {
	        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
	    }
	 
	 //TextBlock....
	    @Override
	    public String toString() {
	        return """
	            User Details:
	            ├── Username: %s
	            └── Email: %s
	            """.formatted(name, age);
	    }
}



interface Display {
    void show();
}

record Employee(String name, int id) implements Display {
    public void show() {
        System.out.println(name + " -> " + id);
    }
    
}

