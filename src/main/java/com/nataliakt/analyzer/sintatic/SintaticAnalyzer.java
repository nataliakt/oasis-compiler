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

	private int firstSemanticAction;
	private States states;
	private LexicalAnalyzer lexicalAnalyzer;
	private SemanticAnalyzer semanticAnalyzer;
	private List<Token> tokens;

	public SintaticAnalyzer(States states, LexicalAnalyzer lexicalAnalyzer, int firstSemanticAction)
	{
		super();
		this.states = states;
		this.lexicalAnalyzer = lexicalAnalyzer;
		this.firstSemanticAction = firstSemanticAction;
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
		tokens = lexicalAnalyzer.analyze(text);
		System.out.println(tokens);
		Stack stack = new Stack();
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

			switch (action.getAction()) {
			case SHIFT:
				stack.shift(action.getValue());
				System.out.println(stack);
				break;
			case REDUCE:
				try {
					int[] prod = states.getGoTo()[action.getValue()];

					stack.reduce(prod[1]);
					String tokenProd = "TOKEN_" + (prod[0] - 1);
					int goTo = states.getStates().get(stack.getState())
							.getActions().get(tokenProd).getValue();
					stack.shift(goTo);

					System.out.print("Desvio: " + goTo + " ");
					System.out.println(stack);
				} catch (Exception e) {
					throw new SitaticReduceException(token + ". " + action +
							". " + stack, e);
				}

				if (t >= 0) {
					t--;
				}
				break;
			case ACC:
				stack.reduce(1);
				System.out.println(stack);
				return true;
			case ACTION:
				Action go = states.getAction(stack.getState(), "TOKEN_" + (firstSemanticAction + action.getValue() - 1));

				if (go == null) {
					throw new NullSitaticActionException(token + ". " + action +
							". " + stack);
				}

				if (semanticAnalyzer != null) {
					semanticAnalyzer.addAction(action.getValue(), token);
				}

				if (stack.getState() != 0) {
					stack.reduce(1);
					System.out.println("Ação: Reduzir, Valor: 1");
				}
				stack.shift(go.getValue());
				System.out.println("Ação: Empilhar, Valor: " + go.getValue());
				System.out.println(stack);

				if (t >= 0) {
					t--;
				}
				break;
			default:
				throw new SitaticActionNotFoundException(token + ". " + action +
						". " + stack);
			}
		}
		return false;
		
	}

	public Stream<Token> getTokens() {
		return tokens.stream();
	}

	public void setSemanticAnalyzer(SemanticAnalyzer semanticAnalyzer) {
		this.semanticAnalyzer = semanticAnalyzer;
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
