package com.epam.dsa.epam.code.aep;

public class Parent{
			protected void display(Parent parent){
				System.out.println("This parent");
			}
		  
		  }
		  
	class Child extends Parent {
			public void display(Parent parent){
				System.out.println("This parent");
			}
		  
			public void display(Child parent){
				System.out.println("This parent");
			}
		 }
		    
	