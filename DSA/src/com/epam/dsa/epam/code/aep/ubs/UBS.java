package com.epam.dsa.epam.code.aep.ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UBS {

	public static void main(String[] args) {
        List<List<Object>> transactions = new ArrayList<>();

        transactions.add(Arrays.asList("A", "B", 100));
        transactions.add(Arrays.asList("B", "C", 50));
        transactions.add(Arrays.asList("A", "C", 30));

        System.out.println(findMinNegativeBalance(transactions));
        
            List<String> words = Arrays.asList("code", "doCe", "ecod", "framer", "frame");

            
         System.out.println(funWithAnagrams(words));
         System.out.println(funWithAnagramsOptimized(words));
         System.out.println(funWithAnaOpt(words));
         
         //sum of          
         findTwoSumPairs(new int[] {2, 8, 3, 7, 5, 1, 5, 9}, 10);
         
         //non repeated character in string
         String str = "swwi";
         System.out.println(findFirstNonRepeatedCharacter(str));
         
         
         //grouping anagrams
         List<String> anagrams = Arrays.asList("code", "doce", "ecod", "framer", "frame");
         System.out.println(groupAnagrams(anagrams));
         
         
         System.out.println(findFirstNonRepeatedCharacterWithLoop("swiss"));
         }
	
	/**
	 * 
	 * @param words
	 * @return
	 */
	public static List<List<String>> groupAnagrams(List<String> words) {
		//Lsit<List<String>> result = new ArrayList<>();
	    return words.stream().collect(Collectors.groupingBy(word -> {
	        char[] chars = word.toCharArray();
	        Arrays.sort(chars);
	        return new String(chars);
	    })).values().stream().toList();
	}
	
	
	
	private static String findFirstNonRepeatedCharacter(String str) {
		String result = Arrays.stream(str.split("")).collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new,Collectors.counting())).
		entrySet().stream().filter(entry -> entry.getValue()==1l).map(Map.Entry::getKey).findFirst().orElse(null);
		//System.out.println(result);
		return result;
	}
	
	/**
	 *  
	 * @param str
	 * @return
	 */
	private static String findFirstNonRepeatedCharacterWithLoop(String str) {
		Map<Character, Integer> charCount = new LinkedHashMap<>();
		
		for(char c : str.toCharArray()) {
			charCount.put(c, charCount.getOrDefault(c, 0) + 1);
		}
		
		for(char c : str.toCharArray()) {
			if(charCount.get(c) ==1)
				return Character.toString(c);
		}
		return "";
	}
	
	
	/**
	 *  Smallest Negative Balance

A group of friends frequently borrow money from each other. You are given a list of transactions representing debts between them. Your task is to determine which person has the smallest (most negative) balance after all transactions are processed.
Each transaction contains three values:
1. Borrower – the person who borrowed money
2. Lender – the person who lent the money
3. Amount – the amount borrowed
When a transaction occurs:
The borrower's balance decreases by the amount.
The lender's balance increases by the amount.
After processing all transactions, determine the person or persons with the smallest negative balance.
If no one has a negative balance, return: "Nobody has a negative balance"

	 * @param transactions
	 * @return
	 */
	    public static List<String> findMinNegativeBalance(List<List<Object>> transactions) {
	        Map<String, Integer> balanceMap = new HashMap<>();

	        // Step 1: Calculate balances
	        for (List<Object> t : transactions) {
	            String borrower = (String) t.get(0);
	            String lender = (String) t.get(1);
	            int amount = (int) t.get(2);

	            balanceMap.put(borrower, balanceMap.getOrDefault(borrower, 0) - amount);
	            balanceMap.put(lender, balanceMap.getOrDefault(lender, 0) + amount);
	        }

	        // Step 2: Find minimum balance
	        int minBalance = Integer.MAX_VALUE;
	        for (int bal : balanceMap.values()) {
	            minBalance = Math.min(minBalance, bal);
	        }

	        // Step 3: If no negative balance
	        if (minBalance >= 0) {
	            return Arrays.asList("Nobody has a negative balance");
	        }

	        // Step 4: Collect all with min balance
	        List<String> result = new ArrayList<>();
	        for (Map.Entry<String, Integer> entry : balanceMap.entrySet()) {
	            if (entry.getValue() == minBalance) {
	                result.add(entry.getKey());
	            }
	        }

	        // Step 5: Sort lexicographically
	        Collections.sort(result);

	        return result;
	    }

/**
Task
Remove words that are anagrams of earlier words in the list.
Return the remaining words sorted lexicographically.

Example
Input : ["code", "doce", "ecod", "framer", "frame"]
Explanation
	code,
	doce   → anagram of "code" → remove
	ecod   → anagram of "code" → remove
	framer,
	frame,

Remaining words: ["code", "framer", "frame"]
After sorting alphabetically: ["code", "frame", "framer"]
 * @param words
 * @return
 */
       public static List<String> funWithAnagrams(List<String> words) {
	            Set<String> seen = new HashSet<>();
	            List<String> result = new ArrayList<>();
	            // both can be cubled in one with HashMap with key as sorted word and value as original word

	            for (String word : words) {
	                char[] chars = word.toCharArray();
	                Arrays.sort(chars);
	                String sorted = new String(chars);

	                if (!seen.contains(sorted)) {
	                    seen.add(sorted);
	                    result.add(word);
	                }
	            }
	            // Sort lexicographically
	            Collections.sort(result);

	            return result;
	        }

/**
 * Optimized version using character frequency instead of sorting with O(n*m) where n is number of words and m is average length of word, 
 * sorting is O(m log m) and frequency counting is O(m)
 * @param words
 * @return
 */
       public static List<String> funWithAnagramsOptimized (List<String> words) {
               Set<String> seen = new HashSet<>();
               List<String> result = new ArrayList<>();

               for (String word : words) {
            	   word = word.toLowerCase();
                   int[] freq = new int[26];

                   // Count characters
                   for (char c : word.toCharArray()) {
                       freq[c - 'a']++;
                   }

                   // Build key
                   StringBuilder keyBuilder = new StringBuilder();
                   for (int count : freq) {
                       keyBuilder.append(count).append('#');
                   }

                   String key = keyBuilder.toString();

                   if (!seen.contains(key)) {
                       seen.add(key);
                       result.add(word);
                   }
               }

               Collections.sort(result);
               return result;
           }
       
       /**
        * Another optimized version using HashMap with key as sorted word and value as original word, this will reduce the time complexity to O(n*m log m) where n is number of words and m is average length of word, sorting is O(m log m) and we are doing it for each word
        * @param words
        * @return
        */
       public static List<String> funWithAnaOpt(List<String> words) {
    	   
    	   //for each word sort it ..  and check in   put in map. and get all values and sort it lexicographically
    	   
    	   Map<String, String> map = new HashMap<>();
    	   
    	   for(String word : words) {
    		   char[] wordArr = word.toLowerCase().toCharArray();
    		   Arrays.sort(wordArr);
    		   if(!map.containsKey(wordArr)){
    			 //  System.out.println(word + " " + new String(wordArr));
    			   map.put(new String(wordArr), word);
			   }    		   
    	   }
    	   List<String> result = new ArrayList<>(map.values());
    	   Collections.sort(result);
    	   return result;
       }
       
    
       /**
		* Given an array of integers and a target sum, find all pairs of indices in the array where the 
		* corresponding values sum up to the target. Each pair should be returned as an array of two indices.
		*  If no pairs are found, return an empty list.
		* @param arr
		* @param target
		* @return
		*/       public static List<int []> findTwoSumPairs(int[] arr, int target) {
    	   Map <Integer, List<Integer>> map = new HashMap<>();
    	   List<int[]> result = new ArrayList<>();
    	   
    	   for(int i =0 ; i<arr.length; i++) {
    		    if(map.containsKey(target - arr[i])) {
    		       for(int index : map.get(target - arr[i])) {
    		    	   result.add(new int[] {index, i});
    		       }
    		    }
    		    map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
    	   }
    	   result.forEach(pair -> System.out.println(pair[0] + ", " + pair[1]));
    	   return result;
    	   }   
       }