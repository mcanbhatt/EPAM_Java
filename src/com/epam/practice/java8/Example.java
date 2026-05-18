package com.epam.practice.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Example {

	public static void main(String arg[]) {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		// Output = 4, 16, 36, 64

		System.out.println(squareEvenNum(numbers));
		
		//List<String> lst = new ArrayList<>();//Arrays.asList("Java", "Python", "C++", "JavaScript", "Ruby");
		// mutable list
		
		
		System.out.println(sortList(Arrays.asList("Java", "Python", "C++", "JavaScript", "Ruby")));
		
		// check numeric
		ArrayList<String> arr = new ArrayList<>(Arrays.asList("123", "abc",null, "456", "def", "789", "ghi"));
		System.out.println(getNumericValue(arr));

	}

	public static List<Integer> squareEvenNum(List<Integer> list) {

		return list.stream().filter(num -> num % 2 == 0).map(i -> i * i).toList();

	}
	
	
	public static List<String> sortList(List<String> lst){

		  for(int i= 0; i<lst.size() ; i++){
			  for(int j= 0; j<lst.size()-i-1 ; j++){
				if(lst.get(j).compareTo(lst.get(j+1)) > 1){
				    String strTemp = lst.get(j);
					lst.set(j,lst.get(j+1));
					lst.set(j+1,strTemp);
				}
			  }
		  }
		  
		  return lst;
		}

public static List<String> getNumericValue(ArrayList<String> arr){

		return arr.stream().filter(str -> str !=null && !str.isBlank() &&  str.matches("-?\\d+(\\.\\d+)?") ).toList();

	}
}