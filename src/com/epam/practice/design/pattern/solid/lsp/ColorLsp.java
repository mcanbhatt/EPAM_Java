package com.epam.practice.design.pattern.solid.lsp;

interface IColor{
	   public void getColor();
	}

class GreenLsp implements IColor {
	   public void getColor() {
	      System.out.println("Green");
	   }
	}

 class BlueLsp implements IColor {
	   public void getColor() {
	      System.out.println("Blue");
	   }
	   
 }


public class ColorLsp{
	   public static void main(String[] args) {
	      IColor color = new BlueLsp();
	      color.getColor();   
	      //output: Blue
	   }
	}