package com.epam.dsa.epam.code.aep;

class backspaceCharacter {
	
	public static void main(String[] args) {
		backspaceCharacter obj = new backspaceCharacter();
		System.out.println(obj.backspaceCompare("ab#c", "ad#c"));
		System.out.println(obj.backspaceCompare("ab##", "c#d#"));
	}
	 public boolean backspaceCompare(String s, String t) {
	        int count =0;
	        StringBuilder strbld = new StringBuilder();
	        StringBuilder strbld2 = new StringBuilder();
	        for(int i =0 ; i<s.length(); i++){
	            if(s.charAt(i) =='#'){
	                count++;
	            }else{
	                if(count !=0 && strbld.length() !=0){                   
	                   strbld.delete( Math.max(0,strbld.length() -count),strbld.length());                   
	                }
	                 count=0;
	                strbld.append(s.substring(i,i+1));
	            }
	        }

	        if(count !=0){
	                   strbld.delete( Math.max(0,strbld.length() -count),strbld.length());  
	        }
	        count=0;
	        for(int i =0 ; i<t.length(); i++){
	            if(t.charAt(i) =='#'){
	                count++;
	            }else{
	                if(count !=0 && strbld2.length() !=0 ){
	                    strbld2.delete( Math.max(0,strbld2.length() -count),strbld2.length());                    
	                }
	                count=0;
	                strbld2.append(t.substring(i,i+1));
	            }
	        }
	        if(count !=0){                   
	                    strbld2.delete( Math.max(0,strbld2.length() -count),strbld2.length());  
	        }

	        return strbld.toString().equals(strbld2.toString());
	        
	    }
}