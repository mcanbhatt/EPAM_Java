package com.epam.practice.design.pattern.solid.ocp;

import java.util.List;
import java.util.stream.Stream;

public class OpenClosedPrinciple {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      		Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
      		Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
      		Product house = new Product("House", Color.BLUE, Size.LARGE);
      		List<Product> products = List.of(apple,tree,house);
      		ColorSpecification colSpec = new ColorSpecification(Color.GREEN);
      		BetterProductFilter betterFilter = new BetterProductFilter();
      		betterFilter.filter(products, colSpec).forEach(p -> System.out.println("Product: " + p.getName() + " is of color: " + p.getColor()));
      		
      		AndSpecification<Product> andSpec = new AndSpecification<>(colSpec, new SizeSpecification(Size.LARGE));
      		betterFilter.filter(products, andSpec).forEach(p -> System.out.println("Product: " + p.getName() + " is of color: " + p.getColor() + " and size: " + p.getSize()));
	}
}

enum Color { RED, GREEN, BLUE}

 enum ShapeType {CIRCLE, SQUARE, RECTANGLE}

 enum Size {SMALL, MEDIUM, LARGE}
 
class Product {
	private Color color;
	//private ShapeType shapeType;
	private Size size;
	private String name;

	public Product(String name, Color color, Size size) {
		this.color = color;
		this.size = size;
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public Size getSize() {
		return size;
	}
	public String getName() {
		return name;
	}
	
}


/**
 * This class violates the Open-Closed Principle because if we want to add a new filter criteria, we need to modify the existing code.
 */
class ProductFilter {
	public Stream<Product> filterByColor(List<Product> products, Color color) {
		for (Product product : products) {
			if (product.getColor() == color) {
				System.out.println("Product: " + product.getName() + " is of color: " + color);
			}
		}
		
		return products.stream().filter(p -> p.getColor() ==color);
		
	}
	
	//gain new requirement to filter by size, we need to modify the existing code which violates the open closed principle
	public Stream<Product> filterBySize(List<Product> products, Size size) {
		for (Product product : products) {
			if (product.getSize() == size) {
				System.out.println("Product: " + product.getName() + " is of size: " + size);
			}
		}
		
		return products.stream().filter(p -> p.getSize() ==size);
	}
	//if we want to filter by shape and color, we need to modify the existing code which violates the open closed principle
}



class Specification<T> {
	public boolean isSatisfied(T items) {
		return true;
	}
}

class ColorSpecification extends Specification<Product> {
	private Color color;
	
	public ColorSpecification(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean isSatisfied(Product item) {
		return item.getColor() == color;
	}
}

class SizeSpecification extends Specification<Product> {
	private Size size;
	
	public SizeSpecification(Size size) {
		this.size = size;
	}
	
	@Override
	public boolean isSatisfied(Product item) {
		return item.getSize() == size;
	}
}

class BetterProductFilter {
	public Stream<Product> filter(List<Product> products, Specification<Product> spec) {
		return products.stream().filter(p -> spec.isSatisfied(p));
	}
}


class AndSpecification<T> extends Specification<T> {
	private Specification<T> first, second;
	
	public AndSpecification(Specification<T> first, Specification<T> second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean isSatisfied(T item) {
		return first.isSatisfied(item) && second.isSatisfied(item);
	}
}
/*
class betterProductFilter {
	public Stream<Product> filterByColor(List<Product> products, Color color) {
		return products.stream().filter(p -> p.getColor() ==color);
	}
	
	public Stream<Product> filterBySize(List<Product> products, Size size) {
	}
} */
	