package com.epam.dsa.epam.code.aep;

import java.util.*;

public class FrequencyGrouping1 {

    public static void main(String[] args) {

        int[] arr = {6,1,2,3,4,5,2,1,9};

        Map<Integer,Integer> freqMap = new HashMap<>();

        for(int num : arr)
            freqMap.put(num, freqMap.getOrDefault(num,0)+1);

        Map<Integer,List<Integer>> result = new TreeMap<>(Collections.reverseOrder());

        for(Map.Entry<Integer,Integer> entry : freqMap.entrySet()) {

            int element = entry.getKey();
            int freq = entry.getValue();

            result.computeIfAbsent(freq,k->new ArrayList<>()).add(element);
        }

        System.out.println(result);
    }
}