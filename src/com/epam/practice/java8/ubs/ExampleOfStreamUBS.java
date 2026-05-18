package com.epam.practice.java8.ubs;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExampleOfStreamUBS {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*sumOfInteger(); sumOfIntegerWithReduce();
		countOfStringWith("A");
		listfuzz();
		evenOdd();
		topKFrequentElemnts();*/
		sumOfIntegerWithReduce();
		joinTwoIntegerArrays();
		predicateA();
		getOutput(15);
	}
	
	/// if sum of integer
	//List<Integer> lst = Arrays.asList(1,2,3,4,5);
	

//Array of integers, find the even numbers, square them and find the sum of the result using stream.	
	private static void sumOfIntegerWithReduce() {
		List<Integer> lst = Arrays.asList(1,2,3,4,5,6);
		int value = lst.stream().filter(n -> n%2==0).map(n ->n*n).reduce(0,(a,b) ->a+b);
		System.out.println(value);
	}
	
//Array of integers, find the even numbers, square them and find the sum of the result using stream.	
	private static void sumOfIntegerWithReduce2() {
		List<Integer> lst = Arrays.asList(1,2,3,4,5,6);
		int value = lst.stream().filter(n -> n%2==0).mapToInt(n ->n*n).sum();
		System.out.println(value);
	}
	
	//Join 2 integer arrays using java 8 concepts
	private static void joinTwoIntegerArrays() {
		List<Integer> arf31 = Arrays.asList(1,2,3,4,5,6);
		Integer[] convArr = arf31.toArray(Integer[]::new);
		List<Integer> arr21 = Arrays.asList(1,2,3,4,5,6);
		Integer[] convArr1 = arr21.toArray(Integer[]::new);
		
		 int[] arr1 = {1, 2, 3};
	        int[] arr2 = {4, 5, 6};

		int[] result = IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .toArray();
		System.out.println(Arrays.toString(result));
		
	       int[] result3 = Stream.of(arr1, arr2)
                   .flatMapToInt(Arrays::stream)
                   .toArray();
	       System.out.println(Arrays.toString(result3));

//3		
		int[] result2 = IntStream.concat(
                Arrays.stream(convArr).mapToInt(Integer::intValue),
                Arrays.stream(convArr1).mapToInt(Integer::intValue)
           ).toArray();
		
		System.out.println(Arrays.toString(result2));
		
		//int[] arr = arf31.stream().mapToInt(Integer::intValue).toArray();

	}
	
	/**
	 * Method reference and predicate example
	 * Input:  ["Apple", "Banana", "Avocado", "Mango"]
	 * 
	 */
	
	private static void predicateA() {
		Predicate<String> startsWithA = str -> str.startsWith("A");
		//"ada".startsWith("A");
		List<String> lst = Arrays.asList("Apple", "Banana", "Avocado", "Mango");
		Long count = lst.stream().filter( startsWithA).count();
		System.out.println(count);
	}
	
	 private  static void getOutput(int num) {
		 Map<Integer, String> rules = new LinkedHashMap<>();
	        rules.put(3, "Fizz");
	        rules.put(5, "Buzz");
	        rules.put(7, "Bazz");

	        StringBuilder res = new StringBuilder();
	        for (Map.Entry<Integer, String> entry : rules.entrySet()) {
	            if (num % entry.getKey() == 0) {
	                res.append(entry.getValue());
	            }
	        }
	        String str = res.length() > 0 ? res.toString() : String.valueOf(num);
	        System.out.println(str);
		 
	        String result = rules.entrySet()
	                .stream()
	                .filter(e -> num % e.getKey() == 0)
	                .map(Map.Entry::getValue)
	                .collect(Collectors.joining());

	        String strs1 = result.isEmpty() ? String.valueOf(num) : result;
	        System.out.println(strs1);
	 }
}

