package com.epam.practice.design.pattern.solid.lsp;
public class ColorthOutLsp{
	   public static void main(String[] args) {
		   Green color = new Blue();
	      color.getColor();   
	      //output: Blue  not correct because we are expecting green color but got blue color, this violates LSP
	   }
	}


class Green {
	   public void getColor() {
	      System.out.println("Green");
	   }
	}

class Blue extends Green {
   public void getColor() {
      System.out.println("Blue");
   }
}



