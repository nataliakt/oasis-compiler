package com.nataliakt.analyzer.lexical.model;

import java.util.HashMap;

/**
 * Estado
 * 
 * @author Natalia Kelim Thiel <natalia.kthiel@gmail.com>
 *
 */
public class State extends HashMap<String, State>
{
	private final String name;
	private final String token;

	public State(String name)
	{
		super();
		this.name = name;
		this.token = name;
	}

	public State(String name, String token)
	{
		super();
		this.name = name;
		this.token = token;
	}

	public State nextState(char character)
	{
		String letter = Character.toString(character);
		State state = null;
		String regex = "";

		for (Entry<String, State> entry : this.entrySet()) {
			if (letter.matches(entry.getKey())) {
				if (state != null) {
					throw new RedundantStatusException("Redundância no estado " + name + ": " + regex + " e " + entry.getKey()
							+ " produziram a mesma resposta no caracter " + letter);
				}
				regex = entry.getKey();
				state = entry.getValue();
			}
		}
		return state;
	}

	public String getName()
	{
		return name;
	}
	
	public String getToken()
	{
		return token;
	}

	public class RedundantStatusException extends RuntimeException
	{
		public RedundantStatusException(String message)
		{
			super(message);
		}

	}

}
