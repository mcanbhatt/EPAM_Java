package com.epam.dsa.practice.alpha.code;

import java.util.Arrays;


public class NextGearterNumber {

	public static void main(String[] args) {
		int[] arr = {3,4,2,1};
		findNextValue(null);
		for(int val : arr) {
			System.out.print(val);
		}
	

	}
	
/**
 * 	Functionality to implement the next greater number of given number .
 * @param arr
 */
	private static void findNextValue(int[] arr) {		
		if(arr ==null || arr.length==0)
			return;
		int index =-1;
	    for(int i=arr.length-1; i >0;i--) {
	    	if(arr[i-1] < arr[i]) {
	    		index=i-1;
	    	}	    		
	    }
	    
	    if(index ==-1) {
	    	Arrays.sort(arr);
	    	return;
	    }
	    
	    //now find the smallest number greater than the current index one right
	    int smallestNumber= Integer.MAX_VALUE;
	    int smallestIndex = Integer.MAX_VALUE;
	    for(int i = index+1; i<arr.length; i++) {
	    	if(arr[index] < arr[i]) {
	    		if(smallestNumber > arr[i]) {
	    			smallestNumber =arr[i];
	    			smallestIndex=i;
	    		}
	    	}
	    }
	    
	    //swap index and smallest index
	    arr[index] = arr[smallestIndex]^arr[index];
	    arr[smallestIndex] = arr[smallestIndex]^arr[index];
	    arr[index] = arr[smallestIndex]^arr[index];
	    	    
		//sort right side of index	     
	  //  sortRemainingArr(arr,index);  //below is the method...
	    Arrays.sort(arr, index+1,arr.length);
		
	}

/**
 * 
 * @param arr
 * @param index
 */
	private static void sortRemainingArr(int[] arr, int index) {		
				for(int i =index+1; i<arr.length; i++) {				
					int currentVal = arr[i];
					int j=i-1;
					while(j > index && arr[j] >currentVal) {
						arr[j+1] = arr[j];
						j--;
					}
					arr[j+1] = currentVal;
				}
	}

}
