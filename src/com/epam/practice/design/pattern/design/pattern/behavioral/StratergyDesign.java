package com.epam.practice.design.pattern.design.pattern.behavioral;

import java.util.List;

public class StratergyDesign {
	public static void main(String[] args) {
		TextProcessor tp = new TextProcessor(OutputFrmt.MARKDOWN);
		tp.appendList(List.of("item1", "item2", "item3"));
		System.out.println(tp);
		System.out.println("Changing output format to HTML...");
		tp.clear();
		tp.setOutputFormat(OutputFrmt.HTML);
		tp.appendList(List.of("item1", "item2", "item3"));
		System.out.println(tp);
		
	}
}

enum OutputFormat {
	HTML, MARKDOWN
}


interface ListStratergy {
	
	default void StartList(StringBuilder sb) {}
	public void AddListItem(StringBuilder sb, List<String> item);
	default void EndList(StringBuilder sb) {}
}


class HtmlListStratergy implements ListStratergy {

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


class MarkdownListStratergy implements ListStratergy {
	@Override
	public void AddListItem(StringBuilder sb, List<String> item) {
		for(String str : item) {
			sb.append("* ").append(str).append("\n");
		}			
	}		
}

class TextProcessor {
	private ListStratergy listStratergy;
	private StringBuilder sb = new StringBuilder();

	public TextProcessor(OutputFrmt format) {
		setOutputFormat(format);
	}

	public void setOutputFormat(OutputFrmt format) {
		switch (format) {
		case OutputFrmt.HTML:
			this.listStratergy = new HtmlListStratergy();
			break;
		case OutputFrmt.MARKDOWN:
			this.listStratergy = new MarkdownListStratergy();
			break;
		}
	}	
	
	public void appendList(List<String> items) {
		listStratergy.StartList(sb);
		listStratergy.AddListItem(sb, items);
		listStratergy.EndList(sb);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public void clear() {
		sb.setLength(0);
	}
}
