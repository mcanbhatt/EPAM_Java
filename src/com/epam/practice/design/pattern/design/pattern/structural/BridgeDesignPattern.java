package com.epam.practice.design.pattern.design.pattern.structural;

/**
 * | Feature        | Decorator               | Bridge                                |
| -------------- | ----------------------- | ------------------------------------- |
| Type           | Behavioral/Structural   | Structural                            |
| Purpose        | Add functionality       | Separate abstraction & implementation |
| Focus          | Enhance object          | Avoid class explosion                 |
| Structure      | Wrapper chain           | Two hierarchies                       |
| Runtime change | Yes                     | Not main goal                         |
| Relationship   | HAS-A (wraps same type) | HAS-A (different hierarchy)           |

 */
public class BridgeDesignPattern {

	public static void main(String[] args) {
		  Device tv = new TV();
		  RemoteControl remote = new BasicRemoteControl(tv);
		  remote.turnOn();
		  remote.setChannel(5);
		  remote.turnOff();

		  Device radio = new Radio();
		  RemoteControl radioRemote = new BasicRemoteControl(radio);
		  radioRemote.turnOn();
		  radioRemote.setChannel(101);
		  radioRemote.turnOff();
		  
		  System.out.println("||"+"=".repeat(55)+"||");
		  System.out.println("||"+"==".repeat(10) + "Second Example" + "==".repeat(10)+"||");
		  System.out.println("||"+"=".repeat(55)+"||");
		  ShapeBridge redCircle = new CircleBridge(new Red());
		  ShapeBridge blueCircle = new CircleBridge(new Blue());
		  redCircle.draw();
		  blueCircle.draw();
		  
	}

}

//Implementation Interface
interface Device {
 void turnOn();
 void turnOff();
 void setChannel(int channel);
}

//Concrete Implementations
class TV implements Device {
 public void turnOn() { System.out.println("TV is ON"); }
 public void turnOff() { System.out.println("TV is OFF"); }
 public void setChannel(int ch) { System.out.println("TV channel: " + ch); }
}

class Radio implements Device {
 public void turnOn() { System.out.println("Radio is ON"); }
 public void turnOff() { System.out.println("Radio is OFF"); }
 public void setChannel(int ch) { System.out.println("Radio frequency: " + ch); }
}

//Abstraction
abstract class RemoteControl {
 protected Device device;
 public RemoteControl(Device device) { this.device = device; }
 public abstract void turnOn();
 public abstract void turnOff();
 public abstract void setChannel(int channel);
}

//Refined Abstraction
class BasicRemoteControl extends RemoteControl {
 public BasicRemoteControl(Device device) { super(device); }
 public void turnOn() { device.turnOn(); }
 public void turnOff() { device.turnOff(); }
 public void setChannel(int channel) { device.setChannel(channel); }
}


//Second Bridge Example of Shape and Color

abstract class ShapeBridge {
	Color color;
	public ShapeBridge(Color color) {
		this.color = color;
	}
	abstract void draw();
}


interface Color{
	void applyColor();
}

class Red implements Color{

	@Override
	public void applyColor() {
		System.out.println("Applying red color");
	}
}

class Blue implements Color{
	 
	@Override
	public void applyColor() {
		System.out.println("Applying blue color");
	}
}


class CircleBridge extends ShapeBridge{
	
	public CircleBridge(Color color) {
		super(color);
	}

	@Override
	void draw() {
		System.out.print("Drawing Circle with ");
		color.applyColor();
	}
}