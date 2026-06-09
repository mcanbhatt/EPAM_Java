package com.epam.dsa.practice.alpha.code;

public class MaxTrailingZerosPath {
    private static int countFactors(int n, int factor) {
        int count = 0;
        while (n > 0 && n % factor == 0) {
            n /= factor;
            count++;
        }
        return count;
    }

    public static int maxTrailingZeros(int[][] grid) {
        int n = grid.length, m = grid[1].length;
        int[][] twos = new int[n][m];
        int[][] fives = new int[n][m];

        // Precompute number of 2s and 5s for each cell
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                twos[i][j] = countFactors(grid[i][j], 2);
                fives[i][j] = countFactors(grid[i][j], 5);
            }

        // Prefix sums in all four directions
        int[][] left2 = new int[n][m], left5 = new int[n][m];
        int[][] right2 = new int[n][m], right5 = new int[n][m];
        int[][] up2 = new int[n][m], up5 = new int[n][m];
        int[][] down2 = new int[n][m], down5 = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                left2[i][j] = twos[i][j] + (j > 0 ? left2[i][j-1] : 0);
                left5[i][j] = fives[i][j] + (j > 0 ? left5[i][j-1] : 0);
            }
            for (int j = m-1; j >= 0; j--) {
                right2[i][j] = twos[i][j] + (j < m-1 ? right2[i][j+1] : 0);
                right5[i][j] = fives[i][j] + (j < m-1 ? right5[i][j+1] : 0);
            }
        }
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                up2[i][j] = twos[i][j] + (i > 0 ? up2[i-1][j] : 0);
                up5[i][j] = fives[i][j] + (i > 0 ? up5[i-1][j] : 0);
            }
            for (int i = n-1; i >= 0; i--) {
                down2[i][j] = twos[i][j] + (i < n-1 ? down2[i+1][j] : 0);
                down5[i][j] = fives[i][j] + (i < n-1 ? down5[i+1][j] : 0);
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // Up + Left (excluding current cell twice)
                int t2 = (i > 0 ? up2[i-1][j] : 0) + (j > 0 ? left2[i][j-1] : 0) + twos[i][j];
                int t5 = (i > 0 ? up5[i-1][j] : 0) + (j > 0 ? left5[i][j-1] : 0) + fives[i][j];
                res = Math.max(res, Math.min(t2, t5));
                // Up + Right
                t2 = (i > 0 ? up2[i-1][j] : 0) + (j < m-1 ? right2[i][j+1] : 0) + twos[i][j];
                t5 = (i > 0 ? up5[i-1][j] : 0) + (j < m-1 ? right5[i][j+1] : 0) + fives[i][j];
                res = Math.max(res, Math.min(t2, t5));
                // Down + Left
                t2 = (i < n-1 ? down2[i+1][j] : 0) + (j > 0 ? left2[i][j-1] : 0) + twos[i][j];
                t5 = (i < n-1 ? down5[i+1][j] : 0) + (j > 0 ? left5[i][j-1] : 0) + fives[i][j];
                res = Math.max(res, Math.min(t2, t5));
                // Down + Right
                t2 = (i < n-1 ? down2[i+1][j] : 0) + (j < m-1 ? right2[i][j+1] : 0) + twos[i][j];
                t5 = (i < n-1 ? down5[i+1][j] : 0) + (j < m-1 ? right5[i][j+1] : 0) + fives[i][j];
                res = Math.max(res, Math.min(t2, t5));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {1, 10, 10},
            {25, 100, 2},
            {5, 2, 10}
        };
        System.out.println(maxTrailingZeros(grid)); // Should print 4
    }
}