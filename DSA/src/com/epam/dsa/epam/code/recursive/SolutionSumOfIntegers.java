package com.epam.dsa.epam.code.recursive;

import java.util.Arrays;

public class SolutionSumOfIntegers {
    /**
     * Given an array of integers, find the sum of all the integers using recursion.
     * if the input is invalid return 0.
     * @param input
     * @return
     */
	public static void main(String[] args) {
		 SolutionSumOfIntegers solution = new SolutionSumOfIntegers();
		 int result = solution.sumOfIntegers(new int[] {1,2,3,4,5});
		 System.out.println(result);		
		
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
    public int sumOfIntegers(int[] input) {
    	if(input == null || input.length == 0) {
    		return 0;
    	}    	
    	return input[0]+sumOfIntegers(Arrays.copyOfRange(input, 1, input.length));
    }
}
