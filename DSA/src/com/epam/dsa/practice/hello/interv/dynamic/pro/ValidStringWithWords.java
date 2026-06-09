package com.epam.dsa.practice.hello.interv.dynamic.pro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DESCRIPTION (inspired by Leetcode.com)
You are provided with a string s and a set of words called wordDict. 
Write a function to determine whether s can be broken down into a sequence of one or more words from wordDict, 
where each word can appear more than once and there are no spaces in s. 
If s can be segmented in such a way, return true; otherwise, return false.
	Input:s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
	Output false
	Input: s = "hellointerview", wordDict = ["hello","interview"]
Output:true
 */
public class ValidStringWithWords {

	public static void main(String[] args) {
		String text = "hellointerview";
		String[] wordDict = {"hello","interview"};
		System.out.println("is word break correct : "+checkforValidity(text, wordDict));		

	}

	/**
	 * Solving it with DP .
	 * @param text
	 * @param words
	 */
	private static boolean checkforValidity(String text, String[] words) {
		int n = text.length();
		boolean[] dp = new boolean[n+1];
		dp[0] =true;
		
		for(int i=1 ;i<=n;i++) {
			for(String str : words) {
				if(i>=str.length()&& dp[i-str.length()]) {
					if(str.equals(text.substring(i-str.length(),i))) {
				        dp[i]=true;
				        break;
					}
				}
			}
		}
		return dp[n];
	}

	
	/**
	 * Other approach 
	 * 
	 */
	
	public boolean wordBreak(String s, List<String> wordDict) {
	    Set<String> wordSet = new HashSet<>(wordDict);
	    boolean[] dp = new boolean[s.length() + 1];
	    dp[0] = true; // Empty string is a valid break
	    for (int i = 1; i <= s.length(); i++) {
	        for (int j = 0; j < i; j++) {
	            String sub = s.substring(j, i);
	            if (dp[j] && wordSet.contains(sub)) {
	                dp[i] = true;
	                break;
	            }
	        }
	    }
	    return dp[s.length()];
	}
}
