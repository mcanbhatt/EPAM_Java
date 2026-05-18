package com.epam.practice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExampleOfStream {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sumOfInteger(); sumOfIntegerWithReduce();
		countOfStringWith("A");
		listfuzz();
		evenOdd();
		topKFrequentElemnts();
	}
	
	/// if sum of integer
	//List<Integer> lst = Arrays.asList(1,2,3,4,5);
	
	private static void sumOfInteger() {
		List<Integer> lst = Arrays.asList(1,2,3,4,5);
		int sum =  lst.stream().filter(n -> n%2==0).mapToInt(Integer::intValue).sum();
		System.out.println(sum);
	}

	// if sum of integer with reduce
	public static void sumOfIntegerWithReduce() {
		List<Integer> lst = Arrays.asList(1,2,3,4,5);
		int sum =  lst.stream().filter(n -> n%2==0).reduce(0, (a,b)->a+b); //reduce(0, Integer::sum);
		System.out.println(sum);
	}
	
	/**
	 * Input:  ["Apple", "Banana", "Avocado", "Mango"]
		Output: 2
	 */
	
	private static void countOfStringWith(String str) {
		List<String> lst = Arrays.asList("Apple", "Banana", "Avocado", "Mango");
		Long count = lst.stream().filter( w -> w.startsWith(str)).count();
		System.out.println(count);
	}
	
	
	public static void listfuzz() {
		List<Integer> lst = Arrays.asList(1,2,3,4,15);
		
		lst.stream().filter(n -> n>=15 && n%15==0).toList().forEach(System.out::println);
		Long count = lst.stream().filter(n -> n>=15 && n%15==0).count();
		System.out.println(count);		
	}
	
	
	private static void evenOdd() {
		List<Integer> lst = Arrays.asList(1,2,3,4,15);
		lst.stream().collect(Collectors.partitioningBy(n -> n%2==0)).forEach((k,v) -> System.out.println(k + " " + v));		
	}
	
	/**
	 * Input: [1,2,2,3,3,3,4], N = 2  
		Output: [3,2]
	 */
	public static void topKFrequentElemnts() {
		List<Integer> lst = Arrays.asList(1,2,2,2,2,2,3,3,3,4,4);
		List result = 
				lst.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).
				entrySet().stream().sorted(Map.Entry.<Integer,Long>comparingByValue().reversed()).limit(2).map(Map.Entry::getKey).toList();

			System.out.println(result);
	}
}

