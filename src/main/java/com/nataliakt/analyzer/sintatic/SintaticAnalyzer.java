package com.nataliakt.analyzer.sintatic;

import com.nataliakt.analyzer.lexical.LexicalAnalyzer;
import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.analyzer.sintatic.model.Action;
import com.nataliakt.analyzer.sintatic.model.Stack;
import com.nataliakt.analyzer.sintatic.model.States;

import java.util.List;
import java.util.stream.Stream;

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
	private List<Token> tokens;
	private Stack stack;
	private boolean accept;

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
		accept = false;
		tokens = lexicalAnalyzer.analyze(text);
		System.out.println(tokens);
		stack = new Stack();
		stack.shift(0);
		System.out.println(stack);
		
		for (int t = 0; t <= tokens.size(); t++) {
            Token token = new Token("DOLLAR", "$$");

			Action action;

			if (tokens.size() != t) {
				token = tokens.get(t);
				System.out.println();
				System.out.println(token);
				if (token.getName().equals("EPSILON")) {
					continue;
				}
				action = states.getAction(stack.getState(), token.getName());
			} else {
				System.out.println();
				System.out.println("$$");
				action = states.getAction(stack.getState(), token.getName());
			}
			System.out.println(action);

			if (action == null) {
				throw new NullSitaticActionException(token + ". " + stack);
			}

			t = doAction(action, token, t);

			if (accept) {
				return true;
			}
		}
		return false;
		
	}

	public int doAction (Action action, Token token, int t) {
		switch (action.getAction()) {
			case SHIFT:
				stack.shift(action.getValue());
				System.out.println(stack);
				break;
			case REDUCE:
				try {
					int[] prod = states.getGoTo()[action.getValue()];

					System.out.print("Reduz: " + prod[1] + ", ");
					stack.reduce(prod[1]);
					String tokenProd = "TOKEN_" + (prod[0] - 1);
					int goTo = states.getStates().get(stack.getState())
							.getActions().get(tokenProd).getValue();
					stack.shift(goTo);

					System.out.println("Desvio: " + goTo);
					System.out.println(stack);

					if (t >= 0) {
						t--;
					}
				} catch (Exception e) {
					throw new SitaticReduceException(token + ". " + action +
							". " + stack, e);
				}
				break;
			case ACC:
				stack.reduce(1);
				System.out.println(stack);
				accept = true;

				break;
			default:
				throw new SitaticActionNotFoundException(token + ". " + action +
						". " + stack);
		}

		return t;
	}

	public Stream<Token> getTokens() {
		return tokens.stream();
	}

	public class SitaticActionNotFoundException extends RuntimeException
	{
		SitaticActionNotFoundException(String message)
		{
			super(message);
		}
	}

	public class NullSitaticActionException extends RuntimeException
	{
		NullSitaticActionException(String message)
		{
			super(message);
		}
	}

	public class SitaticReduceException extends RuntimeException
	{
		SitaticReduceException(String message, Throwable throwable)
		{
			super(message, throwable);
		}
	}

}
