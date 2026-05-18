package com.epam.practice.design.pattern.solid.lsp;

public class Rectangle_fix implements Shape {

	private int width;
    private int height;

    Rectangle_fix(int w, int h) {
        width = w;
        height = h;
    }

    public int getArea() {
        return width * height;
    }

}
