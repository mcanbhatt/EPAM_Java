package com.epam.dsa.practice.hello.interv.dynamic.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**(
 * Rotate Image
 * Write a function to rotate an n x n 2D matrix representing an image by 90 degrees clockwise. 
 * The rotation must be done in-place, meaning you should modify the input matrix directly
 *  without using an additional matrix for the operation.
 * matrix = [
    [1,4,7],
    [2,5,8],
    [3,6,9]
]
  
  [
    [3,2,1],
    [6,5,4],
    [9,8,7]
]
 */
public class RotateMatrix {
	
	public static void main(String[] str) {
		int[][] matrix = {
		          {1,4,7},
		          {2,5,8},
		          {3,6,9}
		};
		
		generateSequenceint(matrix);
		System.out.println("[");
		for(int i=0 ;i<matrix.length;i++) {
			System.out.print("[");
			for(int j=0; j<matrix[0].length; j++) {
				if(j == matrix[0].length-1) {
					System.out.print(matrix[i][j]);
				}else {
					System.out.print(matrix[i][j]+",");
				}
		}
			if(i==matrix.length-1) {
				System.out.print("]");
			}else {
				System.out.print("],");
			}
			System.out.println();
		}
		System.out.print("]");
	}
	
	
	
	public static void generateSequenceint(int[][] array){		
		if(array ==null || array.length ==0)
			return ;
		
		//transpose		
		for(int i=0 ;i<array.length;i++) {
			for(int j=i; j<array[0].length; j++) {
				int temp = array[i][j];
				array[i][j] = array[j][i];
				array[j][i] = temp;					
			}
		}		
		
		// swap columns
		int left =0;
		int right =array[0].length-1;
		
		while(right > left) {			
			for(int i=0 ; i<array.length; i++) {
				int temp = array[i][right];
				array[i][right] =array[i][left]  ;
				array[i][left] = temp;
					
			}
			right--;
			left++;
		}
		
	}

}
