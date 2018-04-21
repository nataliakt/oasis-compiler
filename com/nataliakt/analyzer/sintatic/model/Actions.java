package com.nataliakt.analyzer.sintatic.model;

import java.util.Map;

public class Actions
{

	private Map<String, Action> actions;

	public Actions(Map<String, Action> actions)
	{
		super();
		this.actions = actions;
	}

	public Map<String, Action> getActions()
	{
		return actions;
	}

	public void setActions(Map<String, Action> actions)
	{
		this.actions = actions;
	}

}
