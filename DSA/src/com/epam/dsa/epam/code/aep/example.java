package com.epam.dsa.epam.code.aep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Given a collection of candidate numbers (candidates) and a target number
 * (target), find all unique combinations in candidates where the candidate
 * numbers sum to target.
 * 
 * Each number in candidates may only be used once in the combination.
 * 
 * Note: The solution set must not contain duplicate combinations.
 * 
 * 
 * Example 1:
 * 
 * Input: candidates = [10,1,2,7,6,1,5], target = 8 Output: [ [1,1,6], [1,2,5],
 * [1,7], [2,6] ] Example 2:
 * 
 * Input: candidates = [2,5,2,1,2], target = 5 Output: [ [1,2,2], [5] ]
 */

public class example {

	public static void main(String[] args) {
		int[] candidates = {10,1,2,7,6,1,5};
		int target = 8;
		Arrays.sort(candidates);
			List<List<Integer>> res = new ArrayList<>();
			List<Integer> current = new ArrayList<>();
			System.out.println(	findTargetSum(candidates,0, target, res, current));

	}
	
 private static List<List<Integer>> findTargetSum(int[] arr, int index, int target,List<List<Integer>> res, List<Integer> current) {
		
		if(target == 0) {
			res.add(new ArrayList<>(current));
			return res;
		}
		if(target < 0) {
			return res;
		}	

		for(int i = index; i<arr.length; i++) {
			if(i > index && arr[i] == arr[index]) continue; // skip duplicates
			current.add(arr[i]);
			findTargetSum(arr, i+1, target-arr[i], res, current);
			current.remove(current.size()-1); // backtrack
		}
		return res;
	}

}

