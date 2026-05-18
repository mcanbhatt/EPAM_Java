package com.epam.practice.design.pattern.solid.lsp;

public class Square_fix implements Shape {

	@Override
	public int getArea() {
		 return side * side;
	}
    private int side;

    Square_fix(int s) {
        this.side = s;
    }

}
