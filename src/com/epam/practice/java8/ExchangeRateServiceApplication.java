package com.epam.practice.java8;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExchangeRateServiceApplication {

	public static void main(String[] args) {
		
	    List<List<String>> sentences = List.of(List.of("The", "Java", "is", "fun"), 
	    		List.of("Java", "is", "powerful", "and", "Java", "is", "fast"),
	            List.of("Coding", "in", "Java", "is", "fun"));
	   List<String> stop = List.of("the", "is", "and", "in");
	 //  Pair
	       
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
	       
	    
	    
		//SpringApplication.run(ExchangeRateServiceApplication.class, args);
	}

}
