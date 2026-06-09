package com.epam.dsa.practice.hello.interv.dynamic.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**(
 * spiral Matrix
 * matrix = [
    [0,1,2],
    [3,4,5],
    [6,7,8]
]
  output [0,1,2,5,8,7,6,3,4]
 */
public class SpiralMatrixPrint {
	
	public static void main(String[] str) {
		int[][] matrix = {
		          {0,1,2},
		          {3,4,5},
		          {6,7,8},
		          {9,10,11}
		};
		
		System.out.println("print --> " +generateSequenceint(matrix));
	}
	
	
	
	public static List<Integer> generateSequenceint(int[][] array){
		
		List<Integer> resultList = new ArrayList<>();		
		if(array ==null || array.length ==0)
			return resultList;
		
		List<List<Integer>> inputList = Arrays.stream(array).
				map(row -> Arrays.stream(row).boxed().collect(Collectors.toList())).collect(Collectors.toList());
		
		while(!inputList.isEmpty()) {
			resultList.addAll(inputList.remove(0));
			//last column
			if(!inputList.isEmpty() && !inputList.get(0).isEmpty()) {
				 for (List<Integer> row : inputList) {
		                resultList.add(row.remove(row.size() - 1));
		            }
			}
			
			// last row
			if(!inputList.isEmpty()) {
				List<Integer> lastRow = inputList.remove(inputList.size()-1);
				Collections.reverse(lastRow);
				resultList.addAll(lastRow);
			}
			
			//first column.
			if(!inputList.isEmpty() && !inputList.get(0).isEmpty()) {				
				for(int i =inputList.size()-1; i>=0;i--) {
					resultList.add(inputList.get(i).remove(0));
				}				
			}
		}
		
		return resultList;
		
	}

}
