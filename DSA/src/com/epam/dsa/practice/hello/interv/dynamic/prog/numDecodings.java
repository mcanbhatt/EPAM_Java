package com.epam.dsa.practice.hello.interv.dynamic.prog;

import java.util.Arrays;

public class numDecodings {

	public static void main(String[] args) {
		String s = "226";
		System.out.println(numDecode(s));
	}

	private static int numDecode(String s) {
		int[] dp = new int[s.length() + 1];

		dp[0] = 1;
		dp[1] = s.charAt(0) != '0' ? 1 : 0;

		for (int i = 2; i <= s.length(); i++) {
			int value = Character.getNumericValue(s.charAt(i - 1));
			if (value >= 0) {
				dp[i] += dp[i - 1];
			}
			
			int twoDigitValue = Integer.parseInt(s.substring(i - 2, i));
			if (twoDigitValue >= 10 && twoDigitValue <= 26) {
				dp[i] += dp[i - 2];
			}

		}
		return dp[s.length()];
	}
}
