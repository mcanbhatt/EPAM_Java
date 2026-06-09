package com.epam.dsa.epam.code.aep;

public class RomanValue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
   System.out.println(intToRoman(17));
	}

	private static String intToRoman(int num) {
		 int[] values = {
		            1000, 900, 500, 400,
		            100, 90, 50, 40,
		            10, 9, 5, 4, 1
		        };

		        String[] symbols = {
		            "M", "CM", "D", "CD",
		            "C", "XC", "L", "XL",
		            "X", "IX", "V", "IV", "I"
		        };
		        
		        StringBuilder strBld = new StringBuilder();
		        
		        for(int i =0 ; i<values.length; i++) {
		        	System.out.println("num="+num+" values["+i+"]="+values[i]+" symbols["+i+"]="+symbols[i]);
		        	while(num>=values[i]) {
		        		
		        		strBld.append(symbols[i]);
		        		num = num - values[i];
		        	}
		        	
		        }
		        return strBld.toString();
	}

}
