package com.nataliakt.analyzer.sintatic.model;

import java.util.Map;

public class States
{

	private Map<Integer, Actions> states;
	private int[][] goTo; // <state, <'token', prodLine>

	public States(Map<Integer, Actions> states, int[][] goTo)
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

	public int[][] getGoTo()
	{
		return goTo;
	}

	public void setGoTo(int[][] goTo)
	{
		this.goTo = goTo;
	}

}
