package com.epam.practice.design.pattern.solid.lsp;

public class Client {

	public static void main(String[] args) {
	//	BirdI bird = new Penguin();
		// bird.fly(); //runtime exception 
		
		// this is to avoid above LSP problem ...
		//if(bird instanceof Penguin){
			   // special handling
			//}
		/**Now problems appear:
			Modify existing code when adding new birds (breaks OCP)
			Classes start handling multiple cases  (breaks SRP)
			So violating LSP causes OCP and SRP violations.
			 to avoid we should do....
			Interface
			   ↑
			Implementations
			   ↑
			Injected into services
		======================================================================	
			If a module strictly follows LSP, then:
				Code depends on abstractions → enabling extension → OCP
				Each subclass handles one specific behavior → SRP
				Thus LSP-compliant designs tend to indirectly satisfy OCP and SRP.
			*/
	}

}
