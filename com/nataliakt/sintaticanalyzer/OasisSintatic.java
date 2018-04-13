package com.nataliakt.sintaticanalyzer;

import java.util.HashMap;
import java.util.Map;

import com.nataliakt.lexicalanalyzer.OasisLexical;
import com.nataliakt.sintaticanalyzer.model.Action;
import com.nataliakt.sintaticanalyzer.model.Action.ActionEnum;
import com.nataliakt.sintaticanalyzer.model.Actions;
import com.nataliakt.sintaticanalyzer.model.States;

public class OasisSintatic extends SintaticAnalyzer
{
	
	private static Map<Integer, Actions> s = new HashMap<>();
	private static Map<Integer, Integer> g = new HashMap<>();
	private static States states = new States(s, g);
	
	public OasisSintatic()
	{
		super(states, new OasisLexical());
		
		Map<String, Action> acs = new HashMap<>();
		acs.put("IF", new Action(ActionEnum.SHIFT, 2));
		s.put(0, new Actions(acs));
		
		acs = new HashMap<>(); // Limpando as actions
		
		acs.put("$", new Action(ActionEnum.ACC, 0));
		s.put(1, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("ABRE_PARENTESES", new Action(ActionEnum.SHIFT, 3));
		s.put(2, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("ID", new Action(ActionEnum.SHIFT, 5));
		acs.put("CONSTANTE", new Action(ActionEnum.SHIFT, 6));
		s.put(3, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("FECHA_PARENTESES", new Action(ActionEnum.SHIFT, 7));
		s.put(4, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("FECHA_PARENTESES", new Action(ActionEnum.REDUCE, 1));
		s.put(5, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("FECHA_PARENTESES", new Action(ActionEnum.REDUCE, 1));
		s.put(6, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("BEGIN", new Action(ActionEnum.SHIFT, 8));
		s.put(7, new Actions(acs));

		acs = new HashMap<>(); // Limpando as actions

		acs.put("END", new Action(ActionEnum.REDUCE, 5));
		s.put(8, new Actions(acs));
		
		g.put(0, 1);
		g.put(3, 4);
	}

}
