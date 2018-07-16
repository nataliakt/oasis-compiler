package com.nataliakt.analyzer.semantic.model.expression;

public class SmallerEqual<T> extends Expression {

    public SmallerEqual(T value) {
        super(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue());
        stringBuilder.append(" <= ");
        stringBuilder.append(getExpression());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
