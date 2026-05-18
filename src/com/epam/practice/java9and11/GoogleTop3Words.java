package com.epam.practice.java9and11;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class GoogleTop3Words {

	public static void main(String[] args) {
		
	    List<List<String>> sentences = List.of(List.of("The", "Java", "is", "fun"), 
	    		List.of("Java", "is", "powerful", "and", "Java", "is", "fast"),
	            List.of("Coding", "in", "Java", "is", "fun"));
	   List<String> stop = List.of("the", "is", "and", "in");
	       
	 /**  sentences.stream()
	        .flatMap(List::stream)
	        .map(String::toLowerCase)
	        .filter(word -> !stop.contains(word)).collect()
	        .forEach(System.out::println);*/
	 //1)flatten list
	    //2 Convert it into Lowercase
	    //3)exclude stop
	    //4)grouping by words with frequency
	    //5) from map extract the output
	    //6) Print the top3 words
	    //7) Collect the output into the list
	    //Expectation: Expected: [java, fun, powerful]

	    List<String> output  = sentences.stream().flatMap(List::stream).map(w->w.toLowerCase())
	            .filter(w->!stop.contains(w))
	            .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,Collectors.counting()))
	            .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
	            .map(Map.Entry::getKey).limit(3).toList();
	    System.out.println(output);    
	    //--------------------
	    
	    List<String> output1  = sentences.stream().flatMap(List::stream).map(w->w.toLowerCase())
	            .filter(w->!stop.contains(w))
	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
	            .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().
	            		thenComparing((e1,e2)->e1.getKey().compareTo(e2.getKey())).reversed())
	            .map(Map.Entry::getKey).limit(3).toList();
	    System.out.println(output1);    
	   //-------------------    
	    
	    List<String> output3  = sentences.stream().flatMap(List::stream).map(w->w.toLowerCase())
	            .filter(w->!stop.contains(w))
	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
	            .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().
	            		thenComparing(Map.Entry::getKey).reversed())
	            .map(Map.Entry::getKey).limit(3).toList();
	    System.out.println(output3);    
	    
	    
	    // combine two lists
	    combineLists(List.of("A", "B", "C"), List.of(1, 2, 3));
	    // remove elements from map
	     Map<String, Integer> map = new HashMap<>();
	    		map.put("A", 30);
	    		map.put("B", 60);
	    		map.put("C", 40);
	    		map.put("D", 50);
	    		map.put("E", 70);
	    removeElementsFromMap(map);
	    countCharacterFrequency("hello");
	}
	
	
	/**
	 * Question 35: Combine Two Lists
		
		Task: Given two lists of equal size, combine them into a list of pairs.
		
		List<String> names = Arrays.asList("A", "B", "C");
		List<Integer> numbers = Arrays.asList(1, 2, 3);
		// Write your solution here
		Expected Output: ["A-1", "B-2", "C-3"]
	 */
	private static List<String> combineLists(List<String> names, List<Integer> numbers) {
	    // Your implementation here
		
		if(names.size() != numbers.size()) {
		    throw new IllegalArgumentException("Both lists must be of equal size.");
	    }
		
		int size = names.size();
		
		List<String> lst = IntStream.range(0,size).mapToObj(i -> "\""+names.get(i) +"-"+numbers.get(i)+"\"").toList();
		
		System.out.println(lst);
	    return null; // Placeholder return statement
	}	
	
	/**
	 * Question 36: Remove Elements from Map

		Task: From a Map<String, Integer>, remove entries where value is less than 50.
		
		Map<String, Integer> map = new HashMap<>();
		map.put("A", 30);
		map.put("B", 60);
		map.put("C", 40);
		// Write your solution here
		Expected Output: {"B"=60}
	 */
	
	private static Map<String, Integer> removeElementsFromMap(Map<String, Integer> map) {
	    // Your implementation here
		// Using removeIf to remove entries with value less than 50 for mutable map
		map.entrySet().removeIf(entry -> entry.getValue() < 40);
		System.out.println(map);
		
// Using Stream API to create a new filtered map for immutable map
		  Map<String, Integer> filtered = map.entrySet().stream()
		      .filter(entry -> entry.getValue() >= 30)
		      .collect(Collectors.toMap(
		          Map.Entry::getKey,
		          Map.Entry::getValue
		      ));
		  
		  System.out.println(filtered);
		  
		  filtered.entrySet().removeIf(entry -> entry.getValue() < 50);
		  System.out.println(filtered);
	    return null; // Placeholder return statement
	}	
	
	/**
	 * Count frequency of each character in a string.
		
		String text = "hello";
		// Write your solution here
		Expected Output: {'h'=1, 'e'=1, 'l'=2, 'o'=1}
	 * 
	 */
	private static Map<Character, Long> countCharacterFrequency(String text) {
	    // Your implementation here
		
		Map<Character, Long> freqMap =  text.chars().mapToObj(i-> Character.toLowerCase((char) i))
				.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		
		System.out.println(freqMap);
		
		Map<Character, Long> frequency = text.chars()
	            .mapToObj(c -> (char) c)
	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	   
		System.out.println(frequency);
	    
		Map<String, Long> freqStr = text.chars()
	            .mapToObj(c -> String.valueOf((char) c))
	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(freqStr);
		
		return null; // Placeholder return statement
	}
}
