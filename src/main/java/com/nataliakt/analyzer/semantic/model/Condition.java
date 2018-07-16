package com.nataliakt.analyzer.semantic.model;

import com.nataliakt.analyzer.semantic.model.expression.Expression;

public class Condition extends Scope {

    private Expression condition;

    public Condition(String name, Scope father, Expression condition) {
        super(name, father);
        this.condition = condition;
    }

}
