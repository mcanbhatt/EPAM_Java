package com.epam.dsa.practice.hello.interv.greedy;

public class Jump1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 2, 2, 1, 0, 4 };
		System.out.println(jump(arr));
	}

	private static boolean jump(int[] arr) {
		int finalIndex= arr.length-1;
		for(int i =arr.length-2;i>=0; i--) {
			
			if(i +arr[i] >= finalIndex) {
				finalIndex = i;
			}
		}
		
		return finalIndex == 0 ;
	}

}
