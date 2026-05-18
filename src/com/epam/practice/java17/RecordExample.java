package com.epam.practice.java17;

import java.util.List;

/**
 * Key Rule
		A record automatically extends java.lang.Record
		Java does not allow multiple inheritance, so you cannot extend any other class

 * If you need deep immutability, wrap the mutable objects:
List.copyOf(), Set.copyOf(), Collections.unmodifiableList()
| Feature                  | Regular Class    | Record                                             |
| ------------------------ | ---------------- | -------------------------------------------------- |
| Extends a class          | Any class        |  Only `java.lang.Record`                           |
| Implements interface     | Yes              | Yes                                                |
| Fields                   | Mutable or final | Always `final` (immutable reference)               |
| Constructor              | Manual           | Auto-generated canonical constructor               |
| Getters                  | Manual           | Auto-generated with field name                     |
| equals/hashCode/toString | Manual           | Auto-generated                                     |
| Mutability               | Optional         | Immutable reference (object inside can be mutable) |

 */
public class RecordExample {
    public static void main(String[] args) {
        Person p = new Person("Alice", 11);

        System.out.println(p.name()); // getter
        System.out.println(p.age());  // getter
        System.out.println(p);        // toString() -> Person[name=Alice, age=25]
    
        Employee emp = new Employee("Bob", 101);
        
        
        // Team 
        Team team = new Team("Dev Team", List.of("Alice", "Bob", "Charlie"));
         System.out.println(team.name()); // Dev Team
		 System.out.println(team.members()); // [Alice, Bob, Charlie]
		// team.members().add("Charlie"); // throws UnsupportedOperationException
		 
    }
}