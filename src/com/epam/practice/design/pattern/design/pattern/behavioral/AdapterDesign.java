package com.epam.practice.design.pattern.design.pattern.behavioral;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class AdapterDesign {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EuropeanPlug europeanPlug = new EuropeanPlug();
		IPlug adapter = new AdapterEuropeanPlug(europeanPlug);
		adapter.connect();
		

	}
}

interface EPlug{
	void connect();
}

interface IPlug{
	void connect();
}

class IndianPlug implements IPlug{
	@Override
	public void connect() {
		System.out.println("Connected to Indian plug");
		
	}	
}

class EuropeanPlug implements EPlug{
	@Override
	public void connect() {
		System.out.println("Connected to European plug");
	}	
}

class AdapterEuropeanPlug implements IPlug{
	
	private EuropeanPlug europeanPlug;
	
	public AdapterEuropeanPlug(EuropeanPlug europeanPlug) {
		this.europeanPlug = europeanPlug;
	}

	@Override
	public void connect() {
		System.out.println("Adapter converting European plug to Indian plug");
		europeanPlug.connect();	
	}	
}



/// Adapter design pattern is a structural design pattern that allows objects with incompatible interfaces to work together.
/// It acts as a bridge between two incompatible interfaces, enabling them to communicate and collaborate effectively. 
///The adapter pattern is often used when you want to use an existing class but its interface does not match the one you need.
class ExecuteTaks{
	
	public void executeTask() {
		System.out.println("Executing task...");
	}
		
	public FutureTask<Integer> executeTask(Callable<Integer> task) {
		System.out.println("Executing task with id: "+task);
		FutureTask futureTask =  new FutureTask( task);
		futureTask.run();
		return futureTask;
	}
	
	
	public FutureTask<Integer> executeTask(Runnable task) {
		 Executors.newFixedThreadPool(1).submit(task);
		
		System.out.println("Executing task with id: "+task);
		FutureTask futureTask =  new FutureTask(task,0);
		futureTask.run();
		return futureTask;
	}
}

