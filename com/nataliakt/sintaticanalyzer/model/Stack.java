package com.nataliakt.sintaticanalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Stack
{
	private List<Integer> pilha;

	public Stack()
	{
		super();
		pilha = new ArrayList<>();
	}

	public void shift(int state)
	{
		pilha.add(state);
	}
	
	public void reduce(int amount)
	{
		try {
			for (int a = 0; a < amount; a++) {
				pilha.remove(pilha.size() - 1);
			}
		} catch (Exception e) {
			
		}
	}
	
	public Integer getState()
	{
		return pilha.get(pilha.size() - 1);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Pilha: ");
		for (Integer state : pilha) {
			builder.append(state);
			builder.append(" ");
		}
		return builder.toString();
	}
	
	
}
