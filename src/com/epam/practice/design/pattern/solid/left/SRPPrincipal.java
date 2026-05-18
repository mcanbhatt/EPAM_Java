package com.epam.practice.design.pattern.solid.left;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class SRPPrincipal {

	public static void main(String[] args) throws FileNotFoundException {
		Journal journal = new Journal();
		journal.addEntry("I cried today");
		journal.addEntry("I ate a bug");	
		//System.out.println(journal);
	//	journal.saveToFile("filePath");//// this violates the single responsibility principle as the Journal class is responsible for both managing journal entries and saving them to a file
		
		//Separating the concerns of managing journal entries and saving them to a file by creating a separate Persistence classx
		String filePath = "C:\\Users\\NaveenBhatt\\temp\\Journal.txt";
		Persistence persistence = new Persistence();
		persistence.saveToFile(journal, filePath,true);
		
		try {
			Runtime.getRuntime().exec("notepad.exe " + filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Journal{
	
	private final List<String> entries = new ArrayList<>();
	private int count = 0;
	
	public void addEntry(String entry) {
		entries.add(""+(++count)+"; " +entry);
	}
	
	public List<String> getEntries() {
		return entries;
	}
	
	public void removeEntry(int index) {
		if(index >= 0 && index < entries.size()) {
			entries.remove(index);
		}
	}
	
	public String toString() {
		return String.join("\n", entries);
	}
	
	// this method violates the single responsibility principle as it is responsible for 
	//both managing journal entries and saving them to a file
public void saveToFile(String filename) throws FileNotFoundException {
		// code to save journal entries to a file
	  System.out.printf("Saving journal entry \n%s \nTo file: %s ", toString(), filename);
	
	  try(PrintStream out = new PrintStream(filename)) {
		  out.println(toString());
	  }
		  // code to write the journal entries to the file
	}
/**	
	public void loadFromFile(String filename) {
		// code to load journal entries from a file
	}
	public void loadFromWeb(String url) {
		// code to load journal entries from a web resource
	}	 ***/
	
}


// this class is responsible for saving and loading journal entries, which is a separate concern from managing the journal entries themselves

class Persistence{
	public void saveToFile(Journal journal, String filename, boolean overwriteFile) throws FileNotFoundException {
		
		if(overwriteFile || new File(filename).exists()) {
			System.out.printf("Saving journal entry \n%s \nTo file: %s ", journal.toString(), filename);
		
			try(PrintStream out = new PrintStream(filename)) {
				  out.println(journal.toString());
			  }
		}
	}
	
	public Journal loadFromFile(String filename) {
		// code to load journal entries from a file
		return new Journal();
	}
	
	public Journal loadFromWeb(String url) {
		// code to load journal entries from a web resource
		return new Journal();
	}	
}