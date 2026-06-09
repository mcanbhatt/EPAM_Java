package com.epam.dsa.epam.code.aep.ubs;
import java.util.*;

public class TwoSumPairs {
    public static void main(String[] args) {
        int[] arr = {2, 8, 3, 7, 5, 5, 1, 9};

        List<int[]> result = findPairs(arr, 10);

        for (int[] pair : result) {
            System.out.println(pair[0] + ", " + pair[1]);
        }
    }

    public static List<int[]> findPairs(int[] arr, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            int complement = target - arr[i];

            if (map.containsKey(complement)) {
                for (int index : map.get(complement)) {
                    result.add(new int[]{index, i});
                }
            }

            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        return result;
    }
}