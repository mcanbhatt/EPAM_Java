package com.epam.dsa.epam.system.code;

import java.util.LinkedHashMap;

public class LRUCache {

	Node<Integer> head = null;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//get the value of the key if the key exists in the cache otherwise return -1
	public int get(int key) {
		//String str = Integer.toBinaryString(10);
		
		return 0;
	}
	
	public void put(int key, int value) {
		
	}
	
	
	

}


// second is using LinkedHashMap
class LRUCacheUsingLinkedHashMap extends LinkedHashMap<Integer, Integer> {
	
	
	int capacity;
	public LRUCacheUsingLinkedHashMap(int capacity) {
		super(capacity, 0.75f, true);
		this.capacity = capacity;
	}
	//get the value of the key if the key exists in the cache otherwise return -1
	public int get(int key) {
		return super.get(key);
	}
	
	public void put(int key, int value) {
		if(size() >= capacity) {
			removeEldestEntry(null);
		}
		super.put(key, value);		
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<Integer, Integer> eldest) {
		return size() > 2;
	}
}



class Node<T>{
	
	private T key;
	private T value;
	private Node prev;
	private Node next;
	
	public Node(T key, T value) {
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
}
