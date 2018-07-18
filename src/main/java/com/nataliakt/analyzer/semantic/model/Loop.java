package com.nataliakt.analyzer.semantic.model;

import com.nataliakt.analyzer.semantic.model.expression.Expression;

import java.util.stream.Collectors;

public class Loop extends Scope {

    private Expression condition;
    private Definition increment;

    public Loop(Scope father, Expression condition) {
        super("WHILE", father);
        this.condition = condition;
    }

    public Loop(String name, Scope father, Expression condition) {
        super(name, father);
        this.condition = condition;
    }

    public void setIncrement(Definition increment) {
        this.increment = increment;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(getName());
        stringBuilder.append(" ");
        stringBuilder.append(condition);
        stringBuilder.append(":");
        stringBuilder.append(increment);
        stringBuilder.append(">");
        stringBuilder.append(getVariables().collect(Collectors.toList()));
        stringBuilder.append(getChildren().collect(Collectors.toList()));
        return stringBuilder.toString();
    }

}
