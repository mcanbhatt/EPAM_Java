package com.epam.dsa.practice.hello.interv.dynamic.prog;

import java.util.Arrays;
import java.util.List;

public class WordBreak {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "leetcode";
		String[] wordDict = {"leet", "code"};
		System.out.println(wordBreak(s, wordDict));
	}

	private static boolean wordBreak(String s, String[] wordDict) {
		
		boolean[] dp = new boolean[s.length() + 1];
		dp[0] = true;
		// there will be two loops, one for the string and another for the word dict
		// check if substring in an index is contains in word dict and the previous index value is true then means this combination is true 
		// otherwise increase the second loop value and now get new substring  and check above again ...
		List<String> wordList = Arrays.asList(wordDict);
		for(int i =1 ; i<=s.length(); i++){
		 for(int j =0 ;j< i; j++){
			if(wordList.contains(s.substring(j,i)) && dp[j] ==true){
				dp[i] =true;
				break;
			}
		
		}
		 
		}

		return dp[s.length()];
	}
}
