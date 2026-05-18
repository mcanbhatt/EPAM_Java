package com.epam.practice.design.pattern.design.pattern.behavioral;

/**
 * Chain of Responsibility Design Pattern is a behavioral design pattern that allows passing request along the chain 
 * of potential handlers until one of them handles the request. 
 * It decouples the sender of a request from its receiver by giving multiple objects a chance to handle the request.
 */
public class ChainResponsibilityDesign {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Creature goblin = new Creature("Goblin", 2, 5);
		System.out.println(goblin);
		
		CreatureModifier root =  new CreatureModifier(goblin);
		
		root.add(new DoubleAttackModifier(goblin));
		root.add(new IncreaseDefenseModifier(goblin));
		System.out.println("After adding Modifier "+goblin);
		
		root.handle();
		System.out.println(goblin);

	}
}


class Creature {
	private String name;
	private int attackPower;
	private int defensePower;

	public Creature(String name, int attackPower, int defensePower) {
		this.name = name;
		this.attackPower = attackPower;
		this.defensePower = defensePower;
	}

	public String getName() {
		return name;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public int getHealth() {
		return defensePower;
	}

	@Override
	public String toString() {
		return "Creature{name='" + name + "', attackPower=" + attackPower + ", defensePower=" + defensePower + "}";
	}

	public void setAttackPower(int value) {
		attackPower *= 2;
	}

	public void setDefensePower(int value) {	
		defensePower += value;
	}
}

class CreatureModifier {
	protected Creature creature;
	protected CreatureModifier next;

	public CreatureModifier(Creature creature) {
		this.creature = creature;
	}

	public void add(CreatureModifier cm) {
		if (next != null) {
			next.add(cm);
		} else {
			next = cm;
		}
	}

	public  void handle() {
		if(next == null) {
			System.out.println("No modifiers left to apply.");
			return;
		}
		 next.handle();
	}
}



class DoubleAttackModifier extends CreatureModifier {

	public DoubleAttackModifier(Creature creature) {
		super(creature);
	}

	@Override
	public void handle() {
		System.out.println("Doubling " + creature.getName() + "'s attack power.");
		creature.setAttackPower(2);
		super.handle();
	}
}

class IncreaseDefenseModifier extends CreatureModifier {

	public IncreaseDefenseModifier(Creature creature) {
		super(creature);
	}

	@Override
	public void handle() {
		System.out.println("Increasing " + creature.getName() + "'s defense power.");
		creature.setDefensePower(3);
		super.handle();
	}
}

