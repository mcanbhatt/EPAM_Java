package com.epam.dsa.epam.code;

import java.util.Arrays;

public class FindLongestWordInGivenLine {
    //https://autocode-next.lab.epam.com/courses/1372/syllabus/5599
   
	public static void main(String[] args) {
		System.out.println(new FindLongestWordInGivenLine().findLongestWord(null));
	}
	public String findLongestWord(String str){
        
		if(str ==null || str.length() ==0) {
			return null;
		}
		String[] words = str.split(" ") ;
		
		String maxWordLen ="";
		
		for(String word : words) {
			if(maxWordLen.length() < word.length()) {
				maxWordLen = word;
			}
			
		}
		
		return maxWordLen;
    }

}
