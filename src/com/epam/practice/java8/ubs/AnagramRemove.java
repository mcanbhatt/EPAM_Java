package com.epam.practice.java8.ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnagramRemove{
	
public static void main(String[] args) {
	List<String> string = List.of("code","doce","ecod","frame");
	System.out.println(" "+ findUniqueListInLex(string));
}

private static List<String> findUniqueListInLex(List<String> str) {
	List<String> result = new ArrayList<>();
	Set<String> setString = new HashSet<>();

	if (str == null || str.size() == 0) {
		return result;
	}

	for (String string : str) {
		String[] strArr = string.split(""); //str.tocharArray();
		Arrays.sort(strArr);
		
		//String strbld = new String(strArr);
		String strbld = Arrays.toString(strArr);
		if (!setString.contains(strbld)) {
			setString.add(strbld);
			result.add(string);
		}
	}

	Collections.sort(result);
	return result;

}
}