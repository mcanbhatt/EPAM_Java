package com.epam.dsa.epam.code.aep;
import java.util.*;

/**
 * 
 * 
 * | Step               | Complexity |
| ------------------ | ---------- |
| Frequency counting | **O(n)**   |
| Grouping           | **O(n)**   |
| Total              | **O(n)**   |

Algorithm

Traverse the array and count frequencies using a HashMap.

Create another map frequency → list of elements.

Traverse the frequency map and group elements by their frequency.

 */

public class FrequencyGrouping {

    public static Map<Integer, List<Integer>> groupByFrequency(int[] arr) {

        Map<Integer, Integer> freqMap = new HashMap<>();

        // count frequencies
        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        Map<Integer, List<Integer>> result = new HashMap<>();

        // group elements by frequency
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {

            int element = entry.getKey();
            int freq = entry.getValue();

            result.computeIfAbsent(freq, k -> new ArrayList<>()).add(element);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] arr = {6,1,2,3,4,5,2,1,9};

        Map<Integer, List<Integer>> res = groupByFrequency(arr);

        System.out.println(res);
    }
}