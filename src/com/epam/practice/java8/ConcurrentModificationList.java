package com.epam.practice.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>() ; // Arrays.asList(1, 2, 3, 4, 5);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		//System.out.println(modifyListException(list));
		//System.out.println(modifyListWithoutException(list));
	    System.out.println(modifyListAdd(list, 0, 7));
		//System.out.println(modifyListAdd(list));

	}
	
	private static List<Integer> modifyListAdd(List<Integer> list, int index, int element) {
		
	/*	for(Integer num : list) {
			if(num == 2) {
				list.add(6); // This will throw ConcurrentModificationException
			}
		}*/
		//list.add(0,7);
		for(int i = 0; i < list.size(); i++) {
			int temp1 = list.get(i);
			list.set(i, element);
			 element = temp1;
		}
		list.add(element);
		return list;
	}

	private static List<Integer> modifyListException(List<Integer> list) {
			for(Integer num : list) {
			if(num == 2) {
				list.remove(num); // This will throw ConcurrentModificationException
			}
		}
			return list;
	}
	
	private static List<Integer> modifyListWithoutException(List<Integer> list) {
		List<Integer> modifiedList = new ArrayList<>(list); // Create a copy of the original list
		/*for(Integer num : modifiedList) {
			if(num == 2) {
				modifiedList.remove(num); // This will not throw ConcurrentModificationException
			}
		}		*/
		
	    Iterator<Integer> iterator = modifiedList.iterator();
	    while (iterator.hasNext()) {
	        if (iterator.next() == 2) {
	            iterator.remove();
	        }
	    }
		
	   // List<Integer> modifiedList = new ArrayList<>(list);
	  //  modifiedList.removeIf(num -> num == 2);
	    return modifiedList;
	}

}
