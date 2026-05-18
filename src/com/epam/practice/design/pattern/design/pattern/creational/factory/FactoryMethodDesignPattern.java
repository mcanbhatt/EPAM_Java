package com.epam.practice.design.pattern.design.pattern.creational.factory;

/**
 * Facotry Design Pattern is a design pattern that provides an interface for creating objects in a super class,
 *  but allows subclasses to alter the type of objects that will be created. 
 *  It is used when we have a super class with multiple sub-classes and based on input, 
 *  we need to return one of the sub-class. 
 *  This pattern takes out the responsibility of instantiating a class from the client program to the factory class.
 */
public class FactoryMethodDesignPattern {
	public static void main(String[] args) {
		Point3 point = Point3.Factory.createCartesianPoint(1, 2);
	}
}


/**
 * Problem statement: We have a Point class that represents a point in 2D space.4
 * We want to create points using either Cartesian coordinates (x, y) or Polar coordinates (rho, theta).
 *  
 * 
 */
enum CoordinateSystem {
	CARTESIAN, POLAR
}

///first solution using multiple constructors
class Point {
	private int x;
	private int y;
	
	public Point(int x, int y ) {   //but this constructor is not enough to create a point using polar coordinates
									// we need another constructor to create a point using polar coordinates
		this.x = x;
		this.y = y;
	}
	
	public Point(double rho, double theta) {
		this((int)(rho * Math.cos(theta)), (int)(rho * Math.sin(theta)));
	}
}

/**
 * ugly code because of the multiple constructors and the logic to convert polar to cartesian is in the constructor
 * we can use factory method design pattern to solve this problem
 */

class PointCoordinate{
	private double x;
	private double y;
	
	public PointCoordinate(double x, double y, CoordinateSystem system) {
		switch(system) {
		case CARTESIAN:
			this.x = x;
			this.y = y;
			break;
		case POLAR:
			this.x = (int)(x * Math.cos(y));
			this.y = (int)(x * Math.sin(y));
			break;
		}
	}
}

// better solution using factory method design pattern
/**
 * In this solution, we have a Point class that has a private constructor and a PointFactory class 
 * that has static methods to create points using either Cartesian or Polar coordinates.
 */
class Point1 {
	 private double x;
	 private double y;
	 
	 private Point1(double x, double y) {
		 this.x = x;
		 this.y = y;
	 }
	 
	public static Point1 createCartesianPoint(double x, double y) {
		return new Point1(x, y);
	}
	
	public static Point1 createPolarPoint(double rho, double theta) {
		return new Point1(rho * Math.cos(theta), rho * Math.sin(theta));
	}
}


//Factory design  

class Point3 {
	 private double x;
	 private double y;
	 
	 private Point3(double x, double y) {
		 this.x = x;
		 this.y = y;
	 }
	 
	 public static class Factory {
		 public static Point3 createCartesianPoint(double x, double y) {
			 return new Point3(x, y);
		 }
		 
		 public static Point3 createPolarPoint(double rho, double theta) {
			 return new Point3(rho * Math.cos(theta), rho * Math.sin(theta));
		 }
	 }
}






