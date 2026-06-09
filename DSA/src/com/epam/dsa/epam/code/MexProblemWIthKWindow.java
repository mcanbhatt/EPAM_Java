package com.epam.dsa.epam.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MexProblemWIthKWindow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {1, 2 ,1, 0, 5, 1, 1, 0};
		System.out.println(findMex(arr,3));
		System.out.println(findMexInLogN(arr,3));
	}

	private static List<Integer> findMexInLogN(int[] arr, int k) {
		
		int n = arr.length;
		List<Integer> result = new ArrayList<>();
		int freq[] = new int[k+1];
		int currentMex = 0;
		//initially fill the frequency array for the first window
		for(int i =0; i<k; i++) {
			if(k>=arr[i]) {
				freq[arr[i]]++;
			}
		}
		
		while(currentMex <= k && freq[currentMex] > 0) {
			System.out.println("currentMex: "+currentMex);
			currentMex++;
		}
		
		result.add(currentMex);
		
	  for(int i =k; i<n; i++) {
		  //added
		  if(k>=arr[i]) {
			  freq[arr[i]]++;
		  }
		  
		  //removed
		   if(k>=arr[i-k]) {
			  freq[arr[i-k]]--;
			  if(arr[i-k] < currentMex && freq[arr[i-k]] == 0) {
				  currentMex = arr[i-k];
			  }
		   }
		   while(currentMex <= k && freq[currentMex] > 0) {
				currentMex++;
			}
		   result.add(currentMex);
		  }
	  return result;
	}

	
	/**
	 * 
	 * @param arr
	 * @param k
	 * @return
	 */
	private static List<Integer> findMex(int[] arr,int k) {
		// TODO Auto-generated method stub
		
		 Map<Integer,Integer> freqMap = new HashMap<>();
	     List<Integer> mexList = new ArrayList<>();

	        for (int i = 0; i < arr.length; i++) {
	            freqMap.put(arr[i], freqMap.getOrDefault(arr[i], 0) + 1);

	            if (i >= k-1) { // when we have a full window of size 3
	                int mex = findMexInWindow(freqMap,k);
	                mexList.add(mex);

	                // remove the element that is sliding out of the window
	                int outgoingElement = arr[i - (k-1)];
	                freqMap.put(outgoingElement, freqMap.get(outgoingElement) - 1);
	                if (freqMap.get(outgoingElement) == 0) {
	                    freqMap.remove(outgoingElement);
	                }
	            }
	        }

	        return mexList;
	    }

		
		private static int findMexInWindow(Map<Integer, Integer> freqMap,int k) {
			for (int i = 0; i <= k; i++) {
				if (!freqMap.containsKey(i)) {
					return i;
				} 
			}
		  
		  return k+1; 
		  }
		
}