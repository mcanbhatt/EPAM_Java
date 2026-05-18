package com.epam.practice.design.pattern.solid.lsp;

public class Square extends Rectangle {
    public void setWidth(int w) {
        width = w;
        height = w;
    }

    public void setHeight(int h) {
        width = h;
        height = h;
    }
}
