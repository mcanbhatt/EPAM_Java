package com.epam.practice.design.pattern.solid.dry;

import java.util.Optional;

public class Dry_Skiss {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		System.out.println( " "+ calculateFinalPrice(100));
		System.out.println( calculateFinalPriceDry(100));
		KeepItNotSimple();
		KeepItSimpleStupid();
		
	}
	
	/**
	 * @param price
	 * @return
	 */
	static double calculateDiscount(double price) {
	    return price * 0.1;
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	static double calculateFinalPrice(double price) {
	    return price - (price * 0.1); // repeated logic
	}
	
	// To avoid repetition, we can call the calculateDiscount method inside calculateFinalPrice
	static double calculateFinalPriceDry(double price) {
	    return price - calculateDiscount(price);
	}
	
	/**
	 * Complicated code with nested null checks
	 * @return
	 */
	static String KeepItNotSimple() {
		User user = new User("users"); // Assume this is fetched from somewhere
		return Optional.ofNullable(user)
	    .map(u -> u.getAddress())
	    .map(a -> a.getCity())
	    .orElse("Unknown");
	}
	
	/**
	 * Complicated code with nested null checks
	 * @return
	 */
	static String KeepItSimpleStupid() {
		User user = new User("users"); // Assume this is fetched from somewhere
		if (user != null && user.getAddress() != null) {
		    return user.getAddress().getCity();
		}
		return "Unknown";
	}
}

class User{
	private String name;
	private Address address;
	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}
	public void setName(String name) {	
}
}
	
class Address{
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}

