package com.epam.dsa.practice.hello.interv.greedy;

public class BuyAndSell {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {7,1,5,3,6,4};
		System.out.println(maxProfit(arr));
	}
	
	private static int maxProfit(int[] arr) {
		int min = arr[0];
		int maxProfit =0;
		
	/*	for(int val : arr){
			if(val < min) {
				min = val;				
			}
			maxProfit = Math.max(maxProfit, val - min);
		}
	*/
		for(int i =0; arr.length > i; i++){
			min = Math.min(min, arr[i]);
			maxProfit = Math.max(maxProfit, arr[i] - min);
		}
		return maxProfit;
		
	}

}
