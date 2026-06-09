package com.epam.dsa.practice.hello.interv.binary.search;

/**
 * 378. Kth Smallest Element in a Sorted Matrix 
 * Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 * 
 */


class SortedMatrixKthSmallest {

    public int kthSmallest(int[][] matrix, int k) {

        int n = matrix.length;

        int low = matrix[0][0];
        int high = matrix[n - 1][n - 1];

        while (low < high) {

            int mid = low + (high - low) / 2;

            int count = countLessEqual(matrix, mid);

            if (count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    private int countLessEqual(int[][] matrix, int target) {

        int n = matrix.length;

        int row = n - 1;
        int col = 0;

        int count = 0;

        while (row >= 0 && col < n) {

            if (matrix[row][col] <= target) {

                // all elements above are also <= target
                count += row + 1;

                col++;
            } else {
                row--;
            }
        }

        return count;
    }
}