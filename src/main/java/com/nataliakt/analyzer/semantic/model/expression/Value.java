package com.nataliakt.analyzer.semantic.model.expression;

public class Value<T> extends Expression {

    public Value(T value) {
        super(value, null);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getValue1());
        return stringBuilder.toString();
    }
}
