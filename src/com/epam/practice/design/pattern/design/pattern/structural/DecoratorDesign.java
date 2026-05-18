package com.epam.practice.design.pattern.design.pattern.structural;

/**
 * Decorator Design Pattern is a structural design pattern that allows behavior to be added to individual objects,
 *  either statically or dynamically, without affecting the behavior of other objects from the same class. 
 *  It is typically used to extend the functionalities of classes in a flexible and reusable way.
 */
public class DecoratorDesign {

	public static void main(String[] args) {
	 	Shape circle = new Circle(5);
		Shape coloredCircle = new ColorShapeDecorator(circle, "Red");
		Shape transparentColoredCircle = new TransparentDecorator(coloredCircle, 0.5);
		System.out.println("=".repeat(50));
		System.out.println("Drawing a simple circle:");
		System.out.println("=".repeat(50));
		circle.draw();
		System.out.println("=".repeat(50));
		System.out.println("\nDrawing a colored circle:");
		System.out.println("=".repeat(50));
		coloredCircle.draw();
		System.out.println("=".repeat(50));
		System.out.println("\nDrawing a transparent colored circle:");
		System.out.println("=".repeat(50));
		transparentColoredCircle.draw();
	}

}

interface Shape{
	void draw();	
}

class Circle implements Shape{
	int radius;
	public Circle(int radius) {
		this.radius = radius;
	}
	@Override
	public void draw() {
		System.out.println("Drawing a circle");
	}
}

class Square implements Shape{
	int side;
	
	public Square(int side) {
		this.side = side;
	}
	@Override
	public void draw() {
		System.out.println("Drawing a square");
	}
}

class Rectangle implements Shape{
	int length;
	int width;
	
	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}
	@Override
	public void draw() {
		System.out.println("Drawing a rectangle");
	}
}


//Base Decorator
class ShapeDecorator implements Shape{
	
	Shape decoratedShape;
	public ShapeDecorator(Shape decoratedShape) {
		this.decoratedShape = decoratedShape;
	}
	
	@Override
	public void draw() {
		decoratedShape.draw();
	}
}
class ColorShapeDecorator extends ShapeDecorator{
	
	String color;
	public ColorShapeDecorator(Shape decoratedShape, String color) {
		super(decoratedShape);
		this.color = color;
	}	
	
	@Override
	public void draw() {
		decoratedShape.draw();
		System.out.println("Adding color: "+color);
	}
	
}


class TransparentDecorator extends ShapeDecorator{
	
	private double transparency;

	public TransparentDecorator(Shape decoratedShape, double transparency) {
		super(decoratedShape);
		this.transparency = transparency;
	}

	@Override
	public void draw() {
		decoratedShape.draw();
		System.out.println("Adding transparency: "+transparency);
	}	
}