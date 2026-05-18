package com.epam.practice.design.pattern.solid.lsp;

public class LSPPrinicple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RectangleLSP rectangle = new RectangleLSP();
		 rectangle.setWidth(5);
		 rectangle.setHeight(10);
		 System.out.println("Area of Rectangle: " + rectangle.getArea());
		 
		 SquareLsp square = new SquareLsp(5);
		 System.out.println("Area of Square: " + square.getArea());
		 
		 RectangleLSP rectangleSquare = new SquareLsp(5);
		 rectangleSquare.setWidth(4); // This will change both width and height to 4
		 System.out.println("Area of Rectangle Square: " + rectangleSquare.getArea() + "--> It sould be "
		 		+ "5*4 = 20"); // This will not give the expected area of 20
		 

	}
}

class RectangleLSP {
	protected int width;
	protected int height;

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getArea() {
		return width * height;
	}
}


class SquareLsp extends RectangleLSP {

	public SquareLsp(int side) {
		this.width = side;
		this.height = side;
	}
	public void setWidth(int width) {
		this.width = width;
		this.height = width; // Ensure height is the same as width for a square
	}

	public void setHeight(int height) {
		this.height = height;
		this.width = height; // Ensure width is the same as height for a square
	}
}
