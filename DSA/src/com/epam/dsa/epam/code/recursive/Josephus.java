package com.epam.dsa.epam.code.recursive;



public class Josephus {

	/** return the index of alive person in the end
	 * n -> number of people
	 * k -> step count
	 * */
	private int findAlivePerson(int n, int k) {
		if (n == 1)
			return 0;
		int resultIndex = findAlivePerson(n - 1, k);
		return (resultIndex + k) % n;

	}
	
	/**
	 * iterative solution
	 * @param args
	 */
	
	private int findAlivePersonIterative(int n, int k) {
		int resultIndex = 0;
		for (int i = 2; i <= n; i++) {
			resultIndex = (resultIndex + k) % i;
		}
		return resultIndex;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Josephus josephus = new Josephus();
		System.out.println(josephus.findAlivePerson(2, 1));
		System.out.println(josephus.findAlivePersonIterative(2, 1));
	}

}