package com.epam.practice.design.pattern.design.pattern.behavioral;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ObserverPattern {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person person = new Person();
		Event<PropertyChangedEventArgs>.Subscription subscription = person.propertyChanged.addHandler(a -> {
			System.out.println(" Property " + a.propertyName + " has changed" + " with new value: " + a.newValue );
		});
		
		try (Event<PropertyChangedEventArgs>.Subscription subscription2 = person.propertyChanged.addHandler(a -> {
			printMessage("Property " + a.propertyName + " has changed" + " with new value: " + a.newValue);
		});) {
			person.setName("John");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		person.setName("Doe");
		try {
			subscription.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		person.setName("DoeAgain");
		
		
	}
	
	public static void printMessage(String message) {
		System.out.println(message);
	}
}

/**
 *  Event class that allows to subscribe to events and fire them with arguments
 * @param <TArgs>
 */
class Event<TArgs>{	
	private int count = 0;	
	Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();	
	
	public Subscription addHandler(Consumer<TArgs> handler) {
		int i = count;
		handlers.put(count++, handler);
		return new Subscription(i, this);
	}
	
	class Subscription implements AutoCloseable {
		private int id;
		private Event<TArgs> event;
		
	public Subscription(int id, Event<TArgs> event) {
			this.id = id;
			this.event = event;
	}

		@Override
		public void close() throws Exception {
			event.handlers.remove(id);
		}
	}
	
	public void fire(TArgs args) {
		for(Consumer<TArgs> handler: handlers.values()) {
			handler.accept(args);
		}
	}		
}

/**
 * Event arguments class that contains information about the property change event
 */
class PropertyChangedEventArgs {
	public Object source;
	public String propertyName;
	public String newValue;
	
	public PropertyChangedEventArgs(Object source, String propertyName, String newValue) {
		this.source = source;
		this.propertyName = propertyName;
		this.newValue = newValue;
	}
}	

/**
 * Person class that has properties and fires property changed events when properties are updated
 */
class Person{	 	
	public Event<PropertyChangedEventArgs> propertyChanged = new Event<>();
	
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		//PropertyChangedEventArgs args = new PropertyChangedEventArgs(this, "age");
		return age;
	}
	
	public void setName(String name) {
		this.name = name;
		propertyChanged.fire(new PropertyChangedEventArgs(this, "name",name));
	}	
}