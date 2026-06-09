package com.epam.dsa.epam.code;

/**
 * Problem:
Together with your friends you decided to go to a park. The public park is very narrow, thus only one group of people can occupy space between grass and the road:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
TAKEN FREE FREE FREE TAKEN FREE FREE FREE FREE TAKEN
—------------------------------
===============================
Part1- You want more room for your party. Hence, you want to find the longest free space. How would you do this?
Time and Space complexity?
 
 */
public class Snippet {
	public int longestFreeSpace(String[] arr) {
	    int maxLength = 0;
	    int currentLength = 0;
	
	    for (String spot : arr) {
	        if (spot.equals("FREE")) {
	            currentLength++;
	        } else {
	            maxLength = Math.max(maxLength, currentLength);
	            currentLength = 0;
	        }
	    }
	
	    // handle last segment
	    maxLength = Math.max(maxLength, currentLength);
	
	    return maxLength;
	}

	public static int[] findLongestOnes(int[] arr) {
	        int maxLen = 0;
	        int maxStart = -1;
	        int maxEnd = -1;
	        int currentStart = -1;
	        int currentLen = 0;
	
	        for (int i = 0; i < arr.length; i++) {
	            if (arr[i] == 1) {
	                if (currentLen == 0) {
	                    currentStart = i;
	                }
	                currentLen++;
	                if (currentLen > maxLen) {
	                    maxLen = currentLen;
	                    maxStart = currentStart;
	                    maxEnd = i;
	                }
	            } else {
	                currentLen = 0;
	            }
	        }
	        return new int[]{maxStart, maxEnd};
	    }
}

