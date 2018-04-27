package com.nataliakt.analyzer.lexical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nataliakt.analyzer.lexical.model.State;
import com.nataliakt.analyzer.lexical.model.Token;

import static com.nataliakt.analyzer.oasis.OasisLexicalConstants.SPECIAL_CASES_KEYS;

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
		List<Token> tokens = new ArrayList<Token>();
		State current = initial;
		char[] characters = text.toCharArray();
		String value = "";

		for (char character : characters) {
			State next = current.nextState(character);

			// Se for o estado final
			if (next == null) {
				tokens.add(new Token(current.getToken(), value));
				value = Character.toString(character);
				next = initial.nextState(character);
				
				if (next == null) {
					tokens.add(new Token(initial.getToken(), value));
					value = "";
					current = initial;
				} else {
					current = next;
				}
			} else {
				value += character;
				current = next;
			}
		}

		tokens.add(new Token(current.getToken(), value));
		tokens.removeIf(token -> token.getName() == "EPSILON");

		for(Token token : tokens) {
            String newToken = specialCases.get(token.getValue());
            if (newToken != null) {
                token.setName(newToken);
            }
		}

		return tokens;
	}

}
