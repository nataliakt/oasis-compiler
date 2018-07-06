package com.nataliakt.analyzer.lexical;

import com.nataliakt.analyzer.lexical.model.State;
import com.nataliakt.analyzer.lexical.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Analizador léxico
 * 
 * @author Natalia Kelim Thiel <natalia.kthiel@gmail.com>
 *
 */
public abstract class LexicalAnalyzer
{

	private State initial;
	private Map<String, String> specialCases;

	public LexicalAnalyzer(State initial, Map<String, String> specialCases)
	{
		super();
		this.initial = initial;
		this.specialCases = specialCases;
	}

	/**
	 * Analiza um texto e retorna os tokens
	 * 
	 * @param text
	 *            Texto de entrada
	 * @return Mapa de tokens no formato (token, valor do token)
	 */
	public List<Token> analyze(String text)
	{
		List<Token> tokens = new ArrayList<>();
		State current = initial;
		char[] characters = text.toCharArray();
		StringBuilder value = new StringBuilder();

		for (char character : characters) {
			State next = current.nextState(character);

			// Se for o estado final
			if (next == null) {
				tokens.add(new Token(current.getToken(), value.toString()));
				value = new StringBuilder(Character.toString(character));
				next = initial.nextState(character);
				
				if (next == null) {
					tokens.add(new Token(initial.getToken(), value.toString()));
					value = new StringBuilder();
					current = initial;
				} else {
					current = next;
				}
			} else {
				value.append(character);
				current = next;
			}
		}

		tokens.add(new Token(current.getToken(), value.toString()));
		tokens.removeIf(token -> token.getName().equals("EPSILON"));

		for(Token token : tokens) {
            String newToken = specialCases.get(token.getValue());
            if (newToken != null) {
                token.setName(newToken);
            }
		}

		return tokens;
	}

}
