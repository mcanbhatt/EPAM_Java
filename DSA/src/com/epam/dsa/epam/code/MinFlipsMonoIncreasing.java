package com.epam.dsa.epam.code;
public class MinFlipsMonoIncreasing {

    public static int minFlips1(String s) {
        int ones = 0;   // count of '1's seen so far
        int flips = 0;  // minimum flips needed

        for (char c : s.toCharArray()) {
            if (c == '1') {
                ones++;  // count '1'
            } else {
                // Either flip this '0' to '1'
                // OR flip all previous '1's to '0'
                flips = Math.min(flips + 1, ones);
            }
        }

        return flips;
    }

    
    public static void main(String[] args) {
        String s = "00110110";
        System.out.println("Minimum flips: " + minFlips1(s));
    }
    
    public class MinFlipsBinaryArray {
        public static int minFlips(int[] arr) {
            int n = arr.length;
            int[] prefixOnes = new int[n + 1];
            int[] suffixZeros = new int[n + 1];

            // prefixOnes[i]: number of 1s in arr[0..i-1]
            for (int i = 1; i <= n; i++) {
                prefixOnes[i] = prefixOnes[i - 1] + (arr[i - 1] == 1 ? 1 : 0);
            }

            // suffixZeros[i]: number of 0s in arr[i..n-1]
            for (int i = n - 1; i >= 0; i--) {
                suffixZeros[i] = suffixZeros[i + 1] + (arr[i] == 0 ? 1 : 0);
            }

            int minFlips = n;
            for (int i = 0; i <= n; i++) {
                // flips = ones in left + zeros in right
                int flips = prefixOnes[i] + suffixZeros[i];
                minFlips = Math.min(minFlips, flips);
            }

            return minFlips;
        }

    }

   }