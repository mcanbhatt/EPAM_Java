package com.epam.practice.design.pattern.design.pattern.creational;


/**
 * Singleton Design Pattern is a software design pattern that restricts the instantiation of a class to a single
 */
public class SingletonDesign {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}


class SingletonPattern{
	
	
	private static SingletonPattern instance;
	
	private SingletonPattern() {
		
	}
	
	public static SingletonPattern getInstance() {
		if(instance == null) {
			instance = new SingletonPattern();
		}
		return instance;
	}
}

class SingletoneConcurrency{
	
	private static SingletoneConcurrency instance;
	
	private SingletoneConcurrency() {
		
	}
	
	public static synchronized SingletoneConcurrency getInstance() {
		if(instance == null) {
			instance = new SingletoneConcurrency();
		}
		return instance;
	}
}


class SingletoneDobleCheck{
	
	private static volatile SingletoneDobleCheck instance;
	
	private SingletoneDobleCheck() {
		
	}
	
	public static SingletoneDobleCheck getInstance() {
		if(instance == null) {
			synchronized(SingletoneDobleCheck.class) {
				if(instance == null) {
					instance = new SingletoneDobleCheck();
				}
			}
		}
		return instance;
	}
}

/**
 * The Singleton Design Pattern is a software design pattern that restricts the instantiation of a class to a single instance and provides a global 
 * point of access to that instance. This pattern is useful when exactly one object is needed to coordinate actions across the system. 
 * The Singleton pattern ensures that a class has only one instance and provides a global point of access to it.
 * # now create singleton class with resolving all the issues related to singleton design pattern
 * 
 * In Java, the Singleton pattern can be implemented in several ways, including:
 * 
 * 1. Eager Initialization: The instance is created at the time of class loading.
 * 2. Lazy Initialization: The instance is created only when it is requested for the first time.
 * 3. Thread-Safe Singleton: Ensures that multiple threads do not create multiple instances of the singleton class.
 * 4. Double-Checked Locking: A more efficient way to ensure thread safety while creating the singleton instance.
 * 
 * Each implementation has its own advantages and disadvantages, and the choice of which one to use depends on the specific requirements of the application.
 * 
 * class should  not break with serializable , cloning, reflection    for Class loader we can use  create singleton class 
 * 
 * Mitigation strategies:

	•  Ensure the Singleton class is loaded by only one class loader (e.g., place it in a common parent class loader).
	•  Use dependency injection or a container that manages singletons at the application level.
	•  For Java EE/Spring, use the framework’s singleton management.

Summary:
You cannot completely prevent ClassLoader-based Singleton breakage in pure Java code. It must be managed by application architecture and class loader hierarchy.
 */


class SingletonWithSerializableCloningReflection{
	 
	private final static  SingletonWithSerializableCloningReflection INSTANCE = new SingletonWithSerializableCloningReflection();
	
	private SingletonWithSerializableCloningReflection() {
	   if(INSTANCE != null) {
		   throw new RuntimeException("Use getInstance() method to create");
	   }
	}
	
	public static SingletonWithSerializableCloningReflection  getInstance() {
		return INSTANCE;
	}
	
	public SingletonWithSerializableCloningReflection readResolve() {
		return INSTANCE;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}

