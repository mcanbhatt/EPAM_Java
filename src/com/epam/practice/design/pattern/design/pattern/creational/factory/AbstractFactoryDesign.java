package com.epam.practice.design.pattern.design.pattern.creational.factory;

import java.util.HashMap;
import java.util.Map;


public class AbstractFactoryDesign {

	public static void main(String[] args) {

	}

}


interface HotDrink {
	void consume();
}


class Tea implements HotDrink {

	@Override
	public void consume() {
		System.out.println("This tea is delicious");
	}

}

class Coffee implements HotDrink {
	@Override
	public void consume() {
		System.out.println("This coffee is delicious");
	}
}

/**
 * 	
 */
interface HotDrinkFactory{
	HotDrink prepare(int amount);
}


class TeaFactory implements HotDrinkFactory {

	@Override
	public HotDrink prepare(int amount) {
		System.out.println("Put tea bag, boil water, pour " + amount + " ml, add lemon, enjoy!");
		return new Tea();
	}
	
}


class CoffeeFactory implements HotDrinkFactory {

	@Override
	public HotDrink prepare(int amount) {	
		System.out.println("Grind some beans, boil water, pour " + amount + " ml, add cream and sugar, enjoy!");
		return new Coffee();
	}
}


class HotDrinkMachine {
	private Map<String, HotDrinkFactory> factories = new HashMap<>();

	public HotDrinkMachine() {
		factories.put("tea", new TeaFactory());
		factories.put("coffee", new CoffeeFactory());
	}

	public HotDrink makeDrink(String type, int amount) {
		HotDrinkFactory factory = factories.get(type.toLowerCase());
		if (factory != null) {
			return factory.prepare(amount);
		}
		throw new IllegalArgumentException("Unknown drink type: " + type);
	}
}

class HotDrinkMachineWithReflection {
	private Map<String, HotDrinkFactory> factories = new HashMap<>();
	//Reflections reflections = new Reflections("com.epam.practice.design.pattern.design.pattern.creational.factory");
    
	public HotDrinkMachineWithReflection() {
	/**	Reflection.getSubTypesOf(HotDrinkFactory.class).forEach(factoryClass -> {
			  try {
				  HotDrinkFactory factory = factoryClass.getDeclaredConstructor().newInstance();
				  String drinkName = factoryClass.getSimpleName().replace("Factory", "").toLowerCase();
				  factories.put(drinkName, factory);
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  });*/	
		
		//Set<Class<? extends HotDrinkFactory>> types = new Reflections("").getSubtypesOf(HotDrinkFactory.class);
	}

	public HotDrink makeDrink(String type, int amount) {
		HotDrinkFactory factory = factories.get(type.toLowerCase());
		if (factory != null) {
			return factory.prepare(amount);
		}
		throw new IllegalArgumentException("Unknown drink type: " + type);
	}
}

