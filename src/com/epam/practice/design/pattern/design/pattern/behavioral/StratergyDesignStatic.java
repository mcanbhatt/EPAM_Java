package com.epam.practice.design.pattern.design.pattern.behavioral;

import java.util.List;
import java.util.function.Supplier;

public class StratergyDesignStatic {
	public static void main(String[] args) {
		TxtProcessor<LstStratergy> tp = new TxtProcessor(MarkdownLstStratergy::new);
		tp.appendList(List.of("item1", "item2", "item3"));
		System.out.println(tp);
		System.out.println("Changing output format to HTML...");
		tp.clear();
		//tp.setOutputFormat(HtmlLstStratergy::new);
		TxtProcessor<LstStratergy> tp2 = new TxtProcessor(HtmlLstStratergy::new);
		tp2.appendList(List.of("item1", "item2", "item3"));
		System.out.println(tp2);
		
	}
}

enum OutputFrmt {
	HTML, MARKDOWN
}


interface LstStratergy {
	
	default void StartList(StringBuilder sb) {}
	public void AddListItem(StringBuilder sb, List<String> item);
	default void EndList(StringBuilder sb) {}
}


class HtmlLstStratergy implements LstStratergy {

	@Override
	public void StartList(StringBuilder sb) {
		sb.append("<ul>\n");
	}

	@Override
	public void AddListItem(StringBuilder sb, List<String> item) {
		for(String str : item) {
			sb.append("  <li>").append(str).append("</li>\n");
		}
	}

	@Override
	public void EndList(StringBuilder sb) {
		sb.append("</ul>\n");
		
	}
	
}


class MarkdownLstStratergy implements LstStratergy {
	@Override
	public void AddListItem(StringBuilder sb, List<String> item) {
		for(String str : item) {
			sb.append("* ").append(str).append("\n");
		}			
	}		
}

class TxtProcessor<LST extends LstStratergy> {
	private LST lstStratergy;
	private StringBuilder sb = new StringBuilder();

	public TxtProcessor(Supplier<LST> lstStratergySupplier) {
		lstStratergy = lstStratergySupplier.get();
	}


	public void appendList(List<String> items) {
		lstStratergy.StartList(sb);
		lstStratergy.AddListItem(sb, items);
		lstStratergy.EndList(sb);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public void clear() {
		sb.setLength(0);
	}
}
