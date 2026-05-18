package com.epam.practice.java9and11;

public class ObjectDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Object obj = new Object();		
		System.out.println(obj.toString());
		
		System.out.println(obj.hashCode());

		System.out.println(obj.getClass());
		
		System.out.println(obj.equals(new Object()));
		//System.out.println(obj.clone());
	}

}
