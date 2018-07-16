package com.nataliakt.analyzer.semantic.model;

import com.nataliakt.analyzer.semantic.model.expression.Expression;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Condition extends Scope {

    private Expression condition;

    public Condition(Scope father, Expression condition) {
        super("IF", father);
        this.condition = condition;
    }

    public Condition(String name, Scope father, Expression condition) {
        super(name, father);
        this.condition = condition;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(getName());
        stringBuilder.append(" ");
        stringBuilder.append(condition);
        stringBuilder.append(">");
        stringBuilder.append(getVariables().collect(Collectors.toList()));
        stringBuilder.append(getChildren().collect(Collectors.toList()));
        return stringBuilder.toString();
    }
}
