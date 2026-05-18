package com.epam.practice.design.pattern.solid.lsp;

public class BirdLSP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

interface BirdI{
    // to do;
}

interface FlyingBird extends BirdI{
    public void fly();
}

interface WalkingBird extends BirdI{
    public void walk();
}
class ParrotLsp implements FlyingBird, WalkingBird{
    public void fly(){ // to do
    	
    }
    public void walk(){ // to do 
    	
    }
}// ok 

class Penguinlsp implements WalkingBird{
    public void fly(){ // to do 
    	}
 
    public void walk(){ // to do 
    	}
 }
