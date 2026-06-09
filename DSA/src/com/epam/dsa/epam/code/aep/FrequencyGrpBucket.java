package com.epam.dsa.epam.code.aep;
import java.util.*;


/**
 * This is the O(n) Bucket Sort solution used in the famous “Top K Frequent Elements” style problems.
 *  It avoids TreeMap and sorting.

The idea is that frequency of any element cannot exceed n, so we can create buckets indexed by frequency.
 */
public class FrequencyGrpBucket {

    public static void main(String[] args) {

        int[] arr = {6,1,2,3,4,5,2,1,9};
        int topfreqEle=1;
        Map<Integer,Integer> freqMap = new HashMap<>();

        // Step 1: count frequency
        for(int num : arr){
            freqMap.put(num, freqMap.getOrDefault(num,0)+1);
        }

        // Step 2: bucket array
        List<Integer>[] bucket = new List[arr.length + 1];

        for(Map.Entry<Integer,Integer> entry : freqMap.entrySet()){

            int element = entry.getKey();
            int freq = entry.getValue();

            if(bucket[freq] == null)
                bucket[freq] = new ArrayList<>();

            bucket[freq].add(element);
        }

        // Step 3: traverse buckets
        List<Map<Integer, List<Integer>>> result = new ArrayList<>();
        
        for(int i = bucket.length-1; i>=0 && result.size() < topfreqEle; i--){

            if(bucket[i] != null){
            	Map map = new HashMap<>();
            	map.put(i, bucket[i]);
            	result.add(map);
                //System.out.println(i + " -> " + bucket[i]);
            }
        }
        
        System.out.println(" " + result);
    }
}