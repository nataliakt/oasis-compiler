package com.nataliakt.analyzer.semantic.model.expression;

public class Mult<T> extends Expression {

    public Mult(T value) {
        super(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue());
        stringBuilder.append(" * ");
        stringBuilder.append(getExpression());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
