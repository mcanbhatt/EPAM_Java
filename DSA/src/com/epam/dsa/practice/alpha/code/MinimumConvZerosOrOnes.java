package com.epam.dsa.practice.alpha.code;


/**
 * 010110   -- it should monotonic ascending   011111  or 000111 0r 1111111
 * can also do with dynamic programming...
 * 
 */
public class MinimumConvZerosOrOnes {

	public static void main(String[] args) {
		// TODO Auto-generated method 
        int[] arr = {1,0,0,1,0,1};
		int count =0; // means we have 
		int flip =0;
		
		for(int i =0 ; i<arr.length;i++) {
			if(arr[i] ==1) {
				count++;
			}else {
				 flip++;
				 flip= Math.min(flip, count);
			}
			
		}
		System.out.println(flip);
		minFlipMonoInc(arr);
		minFlipMonoIncWithGreedyAppr(arr);

	}
		
		public static void minFlipMonoInc(int[] arr) {
			
			int[] dp = new int[arr.length];
			int ones =0;
			for(int i= 0; i<arr.length; i++) {
				if(arr[i]==1) {
					 dp[i]= i==0?0: dp[i-1];
					 ones++;
				}else {
					int value = i==0?0: dp[i-1]+1;
					dp[i] = Math.min(value, ones);
				}
			}
			System.out.println("  "+dp[arr.length-1]);
		}
		
		
	public static void minFlipMonoIncWithGreedyAppr(int[] arr) {
			
			int[] prefix = new int[arr.length+1]; // convert all 1 from left
			int[] suffix = new int[arr.length+1];// conver all 0 from rights;
			int ones =0;
			for(int i= 0; i<arr.length; i++) {
				
				prefix[i+1] = prefix[i] + (arr[i]==1?1:0);
				//System.out.print(" "+ i+"-"+prefix[i+1]);
			}
			System.out.println();
			for(int i= arr.length-1; i>=0; i--) {
				suffix[i] = suffix[i+1] + (arr[i]==0?1:0);
				//System.out.print(" "+i +"-"+suffix[i]);
			}
			int result=Integer.MAX_VALUE;
			for(int i= 0; i<=arr.length; i++) {
				result = Math.min(suffix[i]+prefix[i], result);
			}
			System.out.println("greedy "+result);
			
		
	}

}
