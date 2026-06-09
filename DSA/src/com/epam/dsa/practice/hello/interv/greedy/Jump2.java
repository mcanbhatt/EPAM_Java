package com.epam.dsa.practice.hello.interv.greedy;

public class Jump2 {

	public static void main(String[] args) {
		int[] arr = { 2, 3, 1, 1, 4, 2, 1, 0, 1, 1 };
		System.out.println(jump(arr));

	}

	private static int jump(int[] arr) {
		
		int currentJump = 0;
		int maxReach = 0;
		int count = 0;
		
		for (int i = 0; i < arr.length - 1; i++) {
			maxReach = Math.max(maxReach, i + arr[i]);
			if (i == currentJump) {
				count++;
				currentJump = maxReach;
				if (currentJump >= arr.length - 1) {
					return count ;
				}
			}
		}
		return count;
	}

}
