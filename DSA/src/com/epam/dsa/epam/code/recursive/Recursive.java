package com.epam.dsa.epam.code.recursive;

import java.math.BigInteger;
import java.util.Arrays;

public class Recursive {
  	public static void main(String[] args) {
		Recursive recursive = new Recursive();
		int result = recursive.sumOfIntegers(new int[] {1,2,3,4,5});
		System.out.println(result);
		
		BigInteger factorialResult = recursive.factorial(BigInteger.valueOf(5));
		System.out.println(factorialResult);
		
	}
	
	public int sumOfIntegers(int[] input) {
	 	if(input == null || input.length == 0) {
	 		return 0;
	 	}    	
	 	return input[0]+sumOfIntegers(Arrays.copyOfRange(input, 1, input.length));
	 }
	
	public BigInteger factorial(BigInteger n) {
	 	if(n.compareTo(BigInteger.ZERO) < 0) {
	 		return BigInteger.ZERO;
 		}
	 	if(n.equals(BigInteger.ONE)) {
	 		return BigInteger.ONE;
	 	}    	
	 	return n.multiply(factorial(n.subtract(BigInteger.ONE)));        
	 }
	
			
}
