package com.epam.practice.java8;

import java.util.List;
import java.util.Optional;

public class OptExample {
	
	   
    /// Handle java7
   // User user = null;
    User user = getUser();
	
    public static void main(String[] args) {
        User user = null;
        System.out.println(user.name); // NullPointerException
        
       // Optional  optuser  = 
       List<String> lst = 
    		   Optional.ofNullable(user).flatMap(usr -> Optional.of(usr.getName())).orElse(null);
       
        ///Java7 handling
        if (user != null) {
	        System.out.println(user.name);
	    } else {
	        System.out.println("User is null");
	    }
        
        //
    }
    
    //javaOptional
    Optional<List<String>> names =
            Optional.ofNullable(user)
                    .map(User::getName);
    
    private User getUser() {
		// TODO Auto-generated method stub
		return null;
	}
  
 

}


class User {
    public List<String> name;
    
    public List<String> getName(){
    	return name;
    	
    }
}

