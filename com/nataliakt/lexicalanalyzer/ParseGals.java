package com.nataliakt.lexicalanalyzer;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nataliakt.lexicalanalyzer.model.State;

public class ParseGals {

	public static LexicalAnalyzer parse(String html) {
		Document doc = Jsoup.parse(html);
		Map<String, State> states = new HashMap<String, State>();
		State initial = null;
		
		// Criando os estados
		Elements linhasEstados = doc.select("table > tbody > tr:gt(1)");
		for (Element linha : linhasEstados) {
			String name = linha.select("td").first().text().trim();
			String token = linha.select("td").get(1).text().trim();
			State state = null;
			if (token.equals(":") || token.equals("?")) {
				state = new State(name);
				if (initial == null) {
					initial = state;
				}
			} else {
				state = new State(name, token);
			}
			states.put(name, state);
		}
		
		// Populando
		Elements chars =  doc.select("table > tbody > tr:eq(1) > td:gt(1)");
		for (Element linha : linhasEstados) {
			String name = linha.select("td").first().text().trim();
			State state = states.get(name);
			Elements tdsState = linha.select("td:gt(1)");
			
			for (int i = 0; i < chars.size(); i++) {
				String stateName = tdsState.get(i).text().trim();
				if (!stateName.equals("-")) {
					String regex = chars.get(i).text().trim();
					if (regex.length() > 0) {
						if (regex.equals("' '")) {
							regex = "\\ ";
						}
						if (regex.equals("\\")) {
							regex = "\\\\";
						}
						if (!regex.contains("\\") && !regex.matches("\\w")) {
							regex = "\\" + regex;
						}
						state.put(regex, states.get(stateName));
					}
				}
			}
		}
		
		return null;
//		return new LexicalAnalyzer(initial);
	}
	
}
