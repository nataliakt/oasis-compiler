package com.nataliakt.analyzer.lexical.model;

/**
 * Token
 * 
 * @author Natalia Kelim Thiel <natalia.kthiel@gmail.com>
 *
 */
public class Token
{

	private String name;
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

	public void setName(String name) {
		this.name = name;
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
