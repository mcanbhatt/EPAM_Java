package com.epam.practice.design.pattern.design.pattern.structural;

public class DecoratorPatternDemo2 {

	public static void main(String[] args) {
		//Usage
		DataSource source = new CompressionDecorator(new EncryptionDecorator(new FileDataSource()));
		source.writeData("sensitive content");
		//Output: Writing plain data to file: ENCRYPTED(COMPRESSED(sensitive content))

	}

}
//1. Component Interface
interface DataSource {
 void writeData(String data);
}

//2. Concrete Component (Basic File Writing)
class FileDataSource implements DataSource {
 public void writeData(String data) {
     System.out.println("Writing plain data to file: " + data);
 }
}

//3. Base Decorator
abstract class DataSourceDecorator implements DataSource {
 protected DataSource wrappee;
 DataSourceDecorator(DataSource source) { this.wrappee = source; }
 public void writeData(String data) { wrappee.writeData(data); }
}

//4. Concrete Decorators
class EncryptionDecorator extends DataSourceDecorator {
 EncryptionDecorator(DataSource source) { super(source); }
 @Override
 public void writeData(String data) {
     String encrypted = "ENCRYPTED(" + data + ")"; // Mock encryption logic
     super.writeData(encrypted);
 }
}

class CompressionDecorator extends DataSourceDecorator {
 CompressionDecorator(DataSource source) { super(source); }
 @Override
 public void writeData(String data) {
     String compressed = "COMPRESSED(" + data + ")"; // Mock compression logic
     super.writeData(compressed);
 }
}
