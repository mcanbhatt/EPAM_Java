package com.epam.practice.design.pattern.solid.left;

import java.util.ArrayList;
import java.util.List;

public class DIPPrinciple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person parent = new Person("John", 40);
		Person child1 = new Person("Chris", 10);
		Person child2 = new Person("Matt", 12);
	    Person child3 = new Person("Sara", 8);
	    Storage storage = new Storage();
	    storage.addParentAndChildRelation(parent, child1);
	    storage.addParentAndChildRelation(parent, child2);
	    storage.addParentAndChildRelation(parent, child3);
	    
	    Research research = new Research(storage);
	    System.out.println(String.join("=").repeat(20));
	    System.out.println("Solution - we can introduce an abstraction layer between high level and low level module");
	    System.out.println(String.join("=").repeat(20));
	    Storage2 storage2 = new Storage2();
	    storage2.addParentAndChildRelation(parent, child1);
	    storage2.addParentAndChildRelation(parent, child2);
	    storage2.addParentAndChildRelation(parent, child3);
	    Research1 research1 = new Research1(storage2);

	}
	
/**1. High level module should not depend on low level module
Both should depend on abstraction

2. Abstraction should not depend on details
Details should depend on abstraction*/

}

enum RelationShip {
	PARENT, CHILD, SIBLING
}


class Person{
	public String name;
	public int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
		//this.relationShip = relationShip;
	}
}


//Low level module - DB - should not depend on high level module - Research 
class Storage {
	
	List<Triplet<Person, RelationShip, Person>> relationParentChildShips = new ArrayList<>();
	public void addParentAndChildRelation(Person parent, Person child) {
		relationParentChildShips.add(new Triplet<>(child, RelationShip.CHILD, parent));
		relationParentChildShips.add(new Triplet<>(parent, RelationShip.PARENT, child));
		
	}
	

	public List<Triplet<Person, RelationShip, Person>> findRelationShip() {
		return relationParentChildShips;
	}
}


/**
 * High level module - Research - should not depend on low level module - DB
 */

class Research {
	public Research(Storage storage) {
		List<Triplet<Person,RelationShip,Person>> relationParentChildShips = storage.findRelationShip();
				
		List<Person> childs =relationParentChildShips.stream().filter(t -> t.first().name.equals("John") 
				&& t.second() == RelationShip.PARENT).map(t -> t.third()).toList();
		for (Person p : childs) {			
			System.out.println("John has a child called " + p.name);
		}
	}
}


record Triplet<T, U, V>(T first, U second, V third) {
	
}



///Solution - we can introduce an abstraction layer between high level and low level module 
//

class Storage2 implements RelationshipBrowser {
	
	List<Triplet<Person, RelationShip, Person>> relationChildParentShips = new ArrayList<>();
	List<Triplet<Person, RelationShip, Person>> relationParentChildShips = new ArrayList<>();
	public void addParentAndChildRelation(Person parent, Person child) {
		relationChildParentShips.add(new Triplet<>(child, RelationShip.CHILD, parent));
		relationParentChildShips.add(new Triplet<>(parent, RelationShip.PARENT, child));
		
	}
	
	public List<Person> findAllChildrenOf(String name){
		//Find all the children of the person with the given name
		return relationParentChildShips.stream().filter(t -> t.first().name.equals(name)
				 && t.second() == RelationShip.PARENT).map(t ->t.third()).toList();
	}
}

interface RelationshipBrowser { 
	List<Person> findAllChildrenOf(String name);
}

// High level module - Research1 - should not depend on low level module - Storage2
class Research1 {
	
	public Research1(RelationshipBrowser browser) {		
		List<Person> parents = browser.findAllChildrenOf("John");
		for (Person p : parents) {			
			System.out.println("John has a child called " + p.name);
		}
	}
}