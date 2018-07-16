package com.nataliakt.analyzer.semantic.model.expression;

public class Different<T> extends Expression {

    public Different(T value) {
        super(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue());
        stringBuilder.append(" ^= ");
        stringBuilder.append(getExpression());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

}
