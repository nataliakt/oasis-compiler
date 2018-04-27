package com.nataliakt.analyzer.sintatic;

import java.util.List;

import com.nataliakt.analyzer.lexical.LexicalAnalyzer;
import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.sintatic.model.Action;
import com.nataliakt.analyzer.sintatic.model.Stack;
import com.nataliakt.analyzer.sintatic.model.States;

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
					System.out.println();
					System.out.println(token);
					if (token.getName().equals("EPSILON")) {
						continue;
					}
					action = states.getAction(stack.getState(), token.getName());
				} else {
					System.out.println();
					System.out.println("$$");
					action = states.getAction(stack.getState(), "DOLLAR");
				}
				System.out.println(action);
				
				switch (action.getAction()) {
				case SHIFT:
					stack.shift(action.getValue());
					System.out.println(stack);
					break;

				case REDUCE:
					int[] prod = states.getGoTo()[action.getValue()];

					stack.reduce(prod[1]);
					String token = "TOKEN_" + (prod[0] - 1);
					int goTo = states.getStates().get(stack.getState())
							.getActions().get(token).getValue();
					stack.shift(goTo);

					System.out.print("Desvio: " + goTo + " ");
					System.out.println(stack);

//                    if (t < tokens.size() - 1) {
                        t--;
//                    }

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
