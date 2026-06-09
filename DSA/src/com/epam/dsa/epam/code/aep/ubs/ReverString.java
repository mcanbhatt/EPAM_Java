package com.epam.dsa.epam.code.aep.ubs;

public class ReverString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "hello world from java";
		System.out.println(reverseString(str));

	}
	
	//"hello world from java"
	
	private static String reverseString(String str) {
		String[] words = str.split(" ");
		StringBuilder reversed = new StringBuilder();
		
		for (int i = words.length - 1; i >= 0; i--) {
			reversed.append(words[i]).append(" ");
		}
		return reversed.toString().trim();
	}

}
