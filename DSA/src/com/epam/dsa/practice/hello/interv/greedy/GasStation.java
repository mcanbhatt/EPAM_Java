package com.epam.dsa.practice.hello.interv.greedy;

public class GasStation {

	public static void main(String[] args) {
		int[] gas = { 5, 2, 0, 3, 3 };
		int[] cost = { 1, 5, 5, 1, 1 };
		
		System.out.print( visitGasStation1(gas, cost));
		
		
	}

	private static int visitGasStation(int[] gas, int[] cost) {
		int gasStation = 0;
		int startingGasStation = 0;
		int currentGas = 0;

		for (int i = 0; i < gas.length; i++) {
			currentGas += gas[i] - cost[i];
			if (currentGas < 0) {
				currentGas = 0;
				continue;
			}
			startingGasStation = i;
			gasStation = (i + 1) % gas.length;
			
			while (gasStation != i && currentGas >= 0) {
				currentGas += gas[gasStation] - cost[gasStation];
				gasStation = (gasStation + 1) % gas.length;
			}
			if (gasStation == i) {
				return startingGasStation;
			}
		}
		return -1;
	}
	
	private static int visitGasStation1(int[] gas, int[] cost) {
		int totalGas = 0;
		int totalCost = 0;
		int start = 0;
		for (int i = 0; i < gas.length; i++) {
			totalGas += gas[i];
			totalCost += cost[i];
		}
		
		if (totalGas < totalCost) {
			return -1;
		}
		int fuel = 0;
		for (int i = 0; i < gas.length; i++) {
			if (fuel + gas[i] - cost[i] < 0) {
				fuel = 0;
				start = i + 1;
			} else {
				fuel += gas[i] - cost[i];
			}
		}
		return start;
	}
}
