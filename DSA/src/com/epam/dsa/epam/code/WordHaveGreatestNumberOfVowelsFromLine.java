package com.epam.dsa.epam.code;

/**
 * 
Write a method that take line as parameter and find the word which has greatest number of vowels in the line.
Example:
Line = "abcde abceee bcd" Output: "abceee"
Description
Among three words in Line, the second word has greatest number of vowels If you words does not have any vowels in the Line, then return null If the line is empty or null then return null.
*/

public class WordHaveGreatestNumberOfVowelsFromLine {
    
	public static void main(String[] args) {
		String line = "   abcde bcoeId abceee   iiiIe   ";
		System.out.println(new WordHaveGreatestNumberOfVowelsFromLine().wordHaveGreatestNumberOfVowelsFromLine(line));
	}
	
	public String wordHaveGreatestNumberOfVowelsFromLine(String line) {

	    if (line == null || line.isBlank()) return null;

	    String[] words = line.trim().split("\\s+");	
	    String result = "";
	    int max = 0;

	    for (String word : words) {
	        int count = 0;
	        for (char c : word.toCharArray()) {
	            if ("aeiouAEIOU".indexOf(c) != -1) count++;
	        }

	        if (count > max) {
	            max = count;
	            result = word;
	        }
	    }

	    return result;
	}
}
