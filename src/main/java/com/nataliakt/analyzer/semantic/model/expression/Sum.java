package com.nataliakt.analyzer.semantic.model.expression;

public class Sum<T> extends Expression {

    public Sum(T value) {
        super(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue());
        stringBuilder.append(" + ");
        stringBuilder.append(getExpression());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
