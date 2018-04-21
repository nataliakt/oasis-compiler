package com.nataliakt.analyzer.sintatic.model;

public class Action {

	private ActionEnum action;
	private Integer value; // Estado ou redução

	public Action(ActionEnum action, Integer value) {
		super();
		this.action = action;
		this.value = value;
	}

	public ActionEnum getAction() {
		return action;
	}

	public void setAction(ActionEnum action) {
		this.action = action;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Ação: ");
		builder.append(action.getDescription());
		builder.append(" Valor:");
		builder.append(value);
		return builder.toString();
	}


	public enum ActionEnum {

		SHIFT("Empilhar"), REDUCE("Reduzir"), ACC("Aceitar");

		private String description;
		
		ActionEnum(String description)
		{
			this.description = description;
		}
		
		public String getDescription()
		{
			return description;
		}
	}
}
