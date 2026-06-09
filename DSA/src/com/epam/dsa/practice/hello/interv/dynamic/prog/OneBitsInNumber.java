package com.epam.dsa.practice.hello.interv.dynamic.prog;

import java.util.Arrays;

public class OneBitsInNumber {
	
	

	public static void main(String[] args) {
		int n = 6;
		System.out.println(Arrays.stream(countOneBits(n)).boxed().toList());
	}

	private static int[] countOneBits(int n) {
		int [] dp = new int[n+1];
		
		for(int i=1; i<=n; i++) {
			dp[i] = dp[i & (i-1)] + 1;
		}		
		System.out.println(Arrays.toString(dp));
		return dp;
	}
}
