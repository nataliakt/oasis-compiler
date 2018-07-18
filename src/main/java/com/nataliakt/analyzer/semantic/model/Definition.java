package com.nataliakt.analyzer.semantic.model;

import com.nataliakt.analyzer.semantic.model.expression.Expression;

import java.util.stream.Collectors;

public class Definition extends Scope {

    private Variable variable;
    private Expression expression;

    public Definition(Variable variable, Expression expression, Scope father) {
        super(variable.getName(), father);
        this.variable = variable;
        this.expression = expression;
    }

    public Definition(Variable variable, Expression expression) {
        super(variable.getName());
        this.variable = variable;
        this.expression = expression;
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append(" = ");
        stringBuilder.append(expression);
        return stringBuilder.toString();
    }
}
