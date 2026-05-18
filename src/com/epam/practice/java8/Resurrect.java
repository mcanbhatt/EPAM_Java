package com.epam.practice.java8;
class Zombie {

    static Zombie saved;

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize called");
        saved = this; // resurrecting the object
    }
}

public class Resurrect {
    public static void main(String[] args) throws Exception {
        Zombie z = new Zombie();

        z = null;          // eligible for GC
        System.gc();

        Thread.sleep(1000);

        if (Zombie.saved != null) {
            System.out.println("Object resurrected!");
        }
    }
}