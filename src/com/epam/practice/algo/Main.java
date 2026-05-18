package com.epam.practice.algo;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("This is first program to check the setup in EPAM");
        int[] arr = {3,6,1,2,8,9,5,1};
		bubleSort(arr);
		Arrays.stream(arr).forEach(System.out::print);
	}
	
   private static void bubleSort(int[] arr) {
	   for(int i=0; i<arr.length ;i++)
		   for(int j=i+1 ; j<arr.length ;j++) {
			   
			   if(arr[i] > arr[j]) {
				   int temp = arr[i];
				   arr[i] = arr[j];
				   arr[j] =temp;
			   }
		   }
	   
	   
   }

}


