package com.epam.practice.java17;

import java.lang.reflect.Field;

/**
 * In Java 17
Error:
java.lang.IllegalAccessError:
class TestUnsafe cannot access class sun.misc.Unsafe

--illegal-access=permit
Java 8 → Internal APIs accessible (unsafe)
Java 9–15 → Warning + --illegal-access workaround
Java 17 and 21 → Strictly blocked (strong encapsulation)
 * @param args
 * @throws Exception
 * 
 * This statement refers to Java 17 and later. In these versions, 
 * the Java Platform Module System (JPMS) enforces strong encapsulation of internal JDK APIs by default. As a result:

	•  Internal JDK packages (like sun.*) are no longer accessible by default.
	•  The JVM flag --illegal-access (used in Java 9–16 to temporarily allow access to internal APIs) is now ignored.
	•  Code that relies on JDK internals will fail unless it uses proper modules or command-line --add-exports/--add-opens options.

This change improves security and maintainability.

Field f = String.class.getDeclaredField("value");
f.setAccessible(true);  // may fail
 */
public class Java17Examples {
	    public static void main(String[] args) throws Exception {
	     /*   Unsafe unsafe = Unsafe.getUnsafe();
	        System.out.println(unsafe);*/
	        
	        Field f = String.class.getDeclaredField("value");
	        f.setAccessible(true);  // may fail
	        System.out.println(f.get("Hello"));
	        
	        /**
	         * Exception in thread "main" java.lang.reflect.InaccessibleObjectException: Unable to make field 
	         * private final byte[] java.lang.String.value accessible: module java.base does not "opens java.lang" to module Practice_Project
	at java.base/java.lang.reflect.AccessibleObject.throwInaccessibleObjectException(AccessibleObject.java:391)
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:335)
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:279
	
	How to fix:
		You must explicitly open the JDK module/package to your module using the --add-opens JVM argument.
		For your case, run your program with:
		java --add-opens java.base/java.lang=ALL-UNNAMED -jar yourprogram.jar
		--add-opens java.base/java.lang=ALL-UNNAMED      JVM argument .
		--add-opens java.base/java.lang=Practice_Project   jvm argument.
	         */
	    }
}
