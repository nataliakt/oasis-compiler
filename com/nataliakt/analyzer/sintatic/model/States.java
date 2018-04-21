package com.nataliakt.analyzer.sintatic.model;

import java.util.Map;

public class States
{

	private Map<Integer, Actions> states;
	private Map<Integer, Integer[]> goTo; // <state, <'token', prodLine>

	public States(Map<Integer, Actions> states, Map<Integer, Integer[]> goTo)
	{
		super();
		this.states = states;
		this.goTo = goTo;
	}
	
	public Action getAction(Integer state, String token)
	{
		return states.get(state)
				.getActions().get(token);
	}

	public Map<Integer, Actions> getStates()
	{
		return states;
	}

	public void setStates(Map<Integer, Actions> states)
	{
		this.states = states;
	}

	public Map<Integer, Integer[]> getGoTo()
	{
		return goTo;
	}

	public void setGoTo(Map<Integer, Integer[]> goTo)
	{
		this.goTo = goTo;
	}

}
