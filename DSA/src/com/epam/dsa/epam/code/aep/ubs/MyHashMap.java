package com.epam.dsa.epam.code.aep.ubs;
import java.util.Objects;

class Node<K, V> {
    K key;
    V value;
    Node<K, V> next;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private Node<K, V>[] buckets;

    public MyHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
    }

    // 🔹 Hash Function
    private int getIndex(K key) {
        return Math.abs(Objects.hashCode(key)) % buckets.length;
    }

    // 🔹 PUT
    public void put(K key, V value) {
        int index = getIndex(key);

        Node<K, V> head = buckets[index];

        // check if key exists
        Node<K, V> curr = head;
        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                curr.value = value; // update
                return;
            }
            curr = curr.next;
        }

        // insert new node (head insertion)
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        buckets[index] = newNode;
    }

    // 🔹 GET
    public V get(K key) {
        int index = getIndex(key);

        Node<K, V> curr = buckets[index];
        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                return curr.value;
            }
            curr = curr.next;
        }

        return null;
    }

    // 🔹 REMOVE
    public void remove(K key) {
        int index = getIndex(key);

        Node<K, V> curr = buckets[index];
        Node<K, V> prev = null;

        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                if (prev == null) {
                    buckets[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }
}