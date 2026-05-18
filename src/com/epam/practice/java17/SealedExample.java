package com.epam.practice.java17;

/**
 * **Exhaustiveness:** When using sealed classes with Switch Expressions, 
 * the compiler knows exactly how many subclasses exist. 
 * This eliminates the need for a default case and catches errors if you forget to handle a new type.
 */
public class SealedExample {

	public static void main(String[] args) {
		 Shape shape = new Square();
		 // expression ...
	
		 /*********************
		  * ### Best Practices

		 * Prefer arrow syntax (`->`) for simple, single-expression cases.
		 * Use `yield` for complex logic requiring multiple statements.
		 * Arrow syntax eliminates fall-through bugs by isolating each case.*/
		 Object result = switch (shape) {
		 	case Circle c -> c;
		 	case Square s ->{
		 			String str = "It's a Square " +s;
		 			yield str; // Use yield to return a value from a block
		 	}
		 	case Triangle t -> "It's a Triangle";
		 };
		 
		 System.out.println(result);
		 
		 
		 int x = 2;

		 switch (x) {
		     case 1 -> System.out.println("One");
		     case 2 -> {
		         System.out.println("Two");
		     }
		 }
		 
		 
		 //old without break 

		 String result1 = switch (x) {
		     case 1 -> "One";
		     case 2 -> "Two";
		     default -> "Other";
		 };

		 System.out.println(result1);
		 
//		 For cases that require **multiple statements** before returning a value, use `yield` (introduced in Java 13). 
	//	 Java 17 continues to support this feature.
//		 ```java
		 Status status = Status.ACTIVE;
		 String yieldresult = switch (status) {
		     case ACTIVE:
		         System.out.println("Processing active user...");
		        // int score = calculateScore();
		         yield "User is active with score: " ;
		     case INACTIVE:
		         System.out.println("Processing inactive user...");
		         yield "User is inactive";
		     default:
		         yield "Unknown status";
		 };
		 
		 System.out.println(yieldresult);

	}

}


 sealed interface Shape permits Circle, Square, Triangle {}

 final class Circle implements Shape {  }
 final class Square implements Shape {  }
 non-sealed class Triangle implements Shape { } // Allows further extension

 
 enum Status{
	 ACTIVE, INACTIVE
 }