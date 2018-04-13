package com.nataliakt.sintaticanalyzer;

import java.util.List;

import com.nataliakt.lexicalanalyzer.LexicalAnalyzer;
import com.nataliakt.lexicalanalyzer.model.Token;
import com.nataliakt.sintaticanalyzer.model.Action;
import com.nataliakt.sintaticanalyzer.model.Stack;
import com.nataliakt.sintaticanalyzer.model.States;

/**
 * Analizador Sintático
 * 
 * @author Natalia Kelim Thiel <natalia.kthiel@gmail.com>
 *
 */
public abstract class SintaticAnalyzer
{

	private States states;
	private LexicalAnalyzer lexicalAnalyzer;

	public SintaticAnalyzer(States states, LexicalAnalyzer lexicalAnalyzer)
	{
		super();
		this.states = states;
		this.lexicalAnalyzer = lexicalAnalyzer;
	}

	/**
	 * Analiza um texto de acordo com os tokens e retorna se é válido
	 * 
	 * @param text
	 *            Texto de entrada
	 * @return Mapa de tokens no formato (token, valor do token)
	 */
	public boolean analyze(String text)
	{
		List<Token> tokens = lexicalAnalyzer.analyze(text);
		System.out.println(tokens);
		Stack stack = new Stack();
		stack.shift(0);
		System.out.println(stack);
		
		for (int t = 0; t <= tokens.size(); t++) {
			try {
				Action action = null;
				
				if (tokens.size() != t) {
					Token token = tokens.get(t);
					if (token.getName().equals("ESPACO")) {
						continue;
					}
					action = states.getAction(stack.getState(), token.getName());
				} else {
					action = states.getAction(stack.getState(), "$");
				}
				System.out.println(action);
				
				switch (action.getAction()) {
				case SHIFT:
					stack.shift(action.getValue());
					System.out.println(stack);
					break;
					
				case REDUCE:
					stack.reduce(action.getValue());
					Integer goTo = states.getGoTo().get(stack.getState());
					stack.shift(goTo);
					System.out.println(stack);
					System.out.print("Desvio: " + goTo + " ");
					if (t < tokens.size() - 1) {
						t--;
					}
					break;
					
				case ACC:
					System.out.println(stack);
					return true;
					
				default:
					System.out.println("Erro sintático!");
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
		
	}

}
