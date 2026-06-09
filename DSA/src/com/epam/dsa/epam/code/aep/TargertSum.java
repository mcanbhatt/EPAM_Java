package com.epam.dsa.epam.code.aep;

import java.util.ArrayList;
import java.util.List;

public class TargertSum {

	public static void main(String[] args) {
	
		int[] arr = {1, 2, 3};
		int target = 5;
		List<List<Integer>> res = new ArrayList<>();
		List<Integer> current = new ArrayList<>();
		findTargetSum(arr,0, target, res, current);
		System.out.println(res);
		
		String s = "abc";
		permuteString(s,0, s.length()-1);
	//	System.out.println(res);
	}
/**
 * Find all permutations of the given string. like ABC -> ABC, ACB, BAC, BCA, CAB, CBA
 * @param s
 * @param i
 * @param j
 */
private static void permuteString(String s, int i, int j) {
	if(i == j) {
		System.out.println(s);
		return;
	}
	System.out.println("i="+i+" j="+j +" s="+s.substring(i, j+1));
	for(int k = i; k<=j; k++) {
		s = swap(s, i, k);
		permuteString(s, i+1, j);
		s = swap(s, i, k); // backtrack
	}
}

private static String swap(String s, int i, int k) {
	// TODO Auto-generated method stub
	char[] charArray = s.toCharArray();
	char temp = charArray[i];
	charArray[i] = charArray[k];
	charArray[k] = temp;
		return String.valueOf(charArray);
}
/**
 * Find all combinations of numbers in the array that sum up to the target.
 * @param arr
 * @param index
 * @param target
 * @param res
 * @param current
 */
	private static void findTargetSum(int[] arr, int index, int target,List<List<Integer>> res, List<Integer> current) {
		
		if(target == 0) {
			res.add(new ArrayList<>(current));
			return;
		}
		if(target < 0) {
			return;
		}
		for (int i =index ;i<arr.length ;i++) {			
			current.add(arr[i]);
			findTargetSum(arr, i, target-arr[i], res, current);
			current.removeLast();			 
		 }
	}
    

}
