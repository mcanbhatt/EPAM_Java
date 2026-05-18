package com.epam.practice.design.pattern.solid.lsp;
public class Main{
   public static void main(String[] args) {
      // violate LSP because color of green object is blue
      Green green = new Blue();
      green.getColor();   
      //output: Blue  
   }
}