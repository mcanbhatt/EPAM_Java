package com.epam.practice.java8;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class StreamQuestions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list1 = List.of(1, 2, 3);
		List<Integer> list2 = List.of(4, 5, 6);
		System.out.println(Stream.concat(list1.stream(), list2.stream()).sorted(Comparator.reverseOrder()).skip(1).findFirst().orElse(null));
	}
	
	

}
