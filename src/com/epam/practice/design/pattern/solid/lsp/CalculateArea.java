package com.epam.practice.design.pattern.solid.lsp;

/**
 * breaks the LSP ...principle 
 */
public class CalculateArea {
	void printArea(Rectangle r) {
	    r.setWidth(5);
	    r.setHeight(4);
	    System.out.println(r.getArea());
	    
	    // fixing abpve issue
	    if (r instanceof Square) {
	        r.setWidth(5);
	    } else {
	        r.setWidth(5);
	        r.setHeight(4);
	    }

	    System.out.println(r.getArea());
	    
	    /**
	     * Now we have problems:
	     *  Violates OCP Every new shape requires modifying the method.
	     *   Violates SRP printArea() now handles multiple behaviors.
	     */
	   
	    
	}
/**
 * printArea(new Rectangle()); // 20
 * printArea(new Square());    // 16 ❌
 * So Square cannot replace Rectangle.
 */
	
	 
    
    /// Again fix
/**
 * printArea(new Rectangle(5,4));
 * printArea(new Square(4));
 * @param shape
 */
    
	void printArea(Shape shape) {
	    System.out.println(shape.getArea());
	}
	
	
	/**
	 * 
	 *  Extended behavior
	 *  No modification
	 *  	This satisfies Open–Closed Principle. if new shape is added this will not impact already existed shapes.
	 * 
	 * Where SRP Appears

Each class now has one responsibility:
		
		Class	Responsibility
		Rectangle	rectangle area logic
		Square	square area logic
		Circle	circle area logic
		Client	printing area
	 */	
}
