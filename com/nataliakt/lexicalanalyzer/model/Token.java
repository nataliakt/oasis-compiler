package com.nataliakt.lexicalanalyzer.model;

/**
 * Token
 * 
 * @author Natalia Kelim Thiel <natalia.kthiel@gmail.com>
 *
 */
public class Token
{

	private final String name;
	private final String value;

	public Token(String name, String value)
	{
		super();
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(name);
		builder.append(",");
		builder.append(value);
		builder.append("> ");
		return builder.toString();
	}

}
