package com.epam.practice.java8;

public class WeakHashmapExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		weakReferenceExample();
	}

	private static void weakReferenceExample() {
		// Create a WeakHashMap
		java.util.WeakHashMap<String, String> weakMap = new java.util.WeakHashMap<>();

		// Add some key-value pairs
		String key1 = new String("key1");
		String key2 = new String("key2");
		weakMap.put(key1, "value1");
		weakMap.put(key2, "value2");

		System.out.println("Before GC: " + weakMap);

		// Remove strong references to the keys
		key1 = null;
		//key2 = null;

		// Suggest garbage collection
		System.gc();

		try {
			Thread.sleep(1000); // Wait for GC to complete
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("After GC: " + weakMap);
		weakMap.remove(key2);
		System.out.println("After removing key2: " + weakMap);
	}

}
