package com.epam.dsa.epam.code.aep;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MaxValueInSubarray {
	public static void main(String[] args) {
		int[] arr = {1,3,-1,-3,5,6,7};
		int k=3;
	   System.out.println(collectValueOfSubarrayK(arr,k));
	}
	
	private static List collectValueOfSubarrayK(int[] arr, int k) {
		 Deque<Integer> dq = new ArrayDeque<>(); // this will not contain value but index 
		 List<Integer> results = new ArrayList<>();
		
		 for(int i = 0 ; i<arr.length ;i++){
		 // check for the peek value is it out range or not
		  // if we are at index 4   ===valid range will be 4-k == 4-3 =1 any value less than this will be invalid
		   if(!dq.isEmpty() && dq.peekFirst().intValue() <= (i-k)) {
			   dq.pollFirst();
		   }
		 
		 /**\
		  * check if current value is greater than value referenced by index at end of deque, then remove all the smaller value 
		  *  and put this in correct place means all should be greater.
		  */  
		    while(!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) {
				   dq.pollLast();
			   }
		    //now add it to deque
		    dq.add(i);
		   System.out.println(dq);
		    if(i >= k-1) {
		    results.add(arr[dq.peekFirst()]);
		    	
		    }		    
		    // now if the size of sliding window is k  then put the peek value to  result		    
		 } 
		 
		 return results;	
	}
	
}
