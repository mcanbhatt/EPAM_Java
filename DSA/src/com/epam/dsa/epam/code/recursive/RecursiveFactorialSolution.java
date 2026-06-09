package com.epam.dsa.epam.code.recursive;

import java.math.BigInteger;

public class RecursiveFactorialSolution {
    /**
     * Given a number n, find the factorial of the number using recursion.
     * @param n
     * @return
     */
	public static void main(String[] args) {
		 RecursiveFactorialSolution solution = new RecursiveFactorialSolution();
		 BigInteger result = solution.factorial(BigInteger.valueOf(5));
		 System.out.println(result);		 
          
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
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
