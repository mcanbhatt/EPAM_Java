package com.epam.practice.design.pattern.solid.lsp;

public class BirdNotLSP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

interface Bird{
    public void fly();
    public void walk();
}


class Parrot implements Bird{
    public void fly(){ // to do
    	
    }
    public void walk(){ // to do 
    	
    }
}// ok 

class Penguin implements Bird{
    public void fly(){ // to do 
    	}
 
    public void walk(){ // to do 
    	}
 }
