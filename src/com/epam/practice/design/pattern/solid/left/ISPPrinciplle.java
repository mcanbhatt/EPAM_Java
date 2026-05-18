package com.epam.practice.design.pattern.solid.left;

public class ISPPrinciplle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}	
	class Document {
		// Document properties and methods
	}
	
	interface Printer {
		void print(Document document);
		void scan(Document document);
		void fax(Document document);
		void copy(Document document);
	}

	// This class adheres to the ISP principle as it implements only the methods relevant to its functionality
	class MultiFunctionPrinter implements Printer {
		@Override
		public void print(Document document) {
			// Implementation for printing
		}
		
		@Override
		public void scan(Document document) {
			// Implementation for scanning
		}
		
		@Override
		public void fax(Document document) {
			// Implementation for faxing
		}
		
		@Override
		public void copy(Document document) {
			// Implementation for copying
		}
	}	
		
	// This class violates the ISP principle as it implements methods that are not relevant to its functionality 
	//and  LSP principle as it cannot be substituted for a MultiFunctionPrinter without throwing exceptions.
	class SimplePrinter implements Printer {
		@Override
		public void print(Document document) {
			// Implementation for printing
		}
		
		@Override
		public void scan(Document document) {
			throw new UnsupportedOperationException("Scan not supported");
		}
		
		@Override
		public void fax(Document document) {
			throw new UnsupportedOperationException("Fax not supported");
		}
		
		@Override
		public void copy(Document document) {
			throw new UnsupportedOperationException("Copy not supported");
		}
	}
	
	
	interface printer {
		void print(Document document);
	}

	interface Scanner{
		
	}
	
	interface Fax{
		
	}
	
	class MultiFunctionPrinter1 implements printer, Scanner, Fax {
		@Override
		public void print(Document document) {
			// Implementation for printing
		}
		
		// Implementations for scanning and faxing would go here
	}
	
	
	/**
	 * 
	 */
	
	class SimplePrinter1 implements printer {
		@Override
		public void print(Document document) {
			// Implementation for printing
		}
	}
	