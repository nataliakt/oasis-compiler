package com.nataliakt.analyzer.semantic.model;

import com.nataliakt.analyzer.semantic.model.expression.Expression;
import com.nataliakt.analyzer.semantic.model.expression.Value;

public class Variable<T> {

    private String type;
    private String name;
    private Expression expression;

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Variable(String type, String name, T value) {
        this.type = type;
        this.name = name;
        this.expression = new Value(value);
    }

    public Variable(String type, String name, Expression expression) {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append(name);
        stringBuilder.append("=");
        stringBuilder.append(expression);
        return stringBuilder.toString();
    }
}
