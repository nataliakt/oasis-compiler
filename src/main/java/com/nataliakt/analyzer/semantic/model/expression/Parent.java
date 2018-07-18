package com.nataliakt.analyzer.semantic.model.expression;

public class Parent<T> extends Expression {

    public Parent(T value) {
        super(value, null);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue1());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
