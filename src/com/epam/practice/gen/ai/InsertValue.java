package com.epam.practice.gen.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertValue{

public static void main(String args[]){
	List<Integer> lst = new ArrayList<>(Arrays.asList(1,2,3,4));
	
	/*
	 * lst.add(1); lst.add(2); lst.add(3); lst.add(4);
	 */
	//Arrays.asList(1,2,3,4);
	System.out.println(lst);
	lst.add(2,8);
	System.out.println(lst);
	
	System.out.println(secondMethod(lst, 3, 10));
}

private static List secondMethod(List<Integer> lst, int index, int value) {
	for(int x = index ; x < lst.size(); x++) {
		int temp = lst.get(x);
		lst.set(x, value);
		value = temp;
	}
	lst.add(value);
	return lst;
}
}