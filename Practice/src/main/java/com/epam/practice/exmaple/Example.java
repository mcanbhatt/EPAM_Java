package com.epam.practice.exmaple;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

public class Example {

	public static void main(String[] args) {
		//System.out.println("Hello, World!");
		List list1 = List.of(1, 2, 3);
		List list2 = List.of(4, 5, 6);
		
		System.out.println(Stream.concat(list1.stream(), list2.stream()).sorted(Comparator.reverseOrder()).skip(1).findFirst().orElse(null));
		
		Collections.unmodifiableList(List.of(1, 2, 3));
		HashMap<String, Integer> map = new HashMap<>();
		
		List<String> list = new ArrayList<>();
		Queue queue = new LinkedList();
		
		queue.add("Hello");
		queue.offer("World");
		queue.offer("Java");
		
		System.out.println(queue);
		System.out.println(queue.peek());
		queue.poll();
		System.out.println(queue.peek());
		queue.remove();
		System.out.println(queue.peek());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		
		Deque<String> stringQueue = new ArrayDeque<>();
		stringQueue.offerLast("Hello");
		stringQueue.offer("World");
		stringQueue.offerFirst("Java");
		System.out.println(stringQueue.peek());
		System.out.println(stringQueue.peekFirst());
		System.out.println(stringQueue.peekLast());
		System.out.println(stringQueue);
		
		

	}

	

}
