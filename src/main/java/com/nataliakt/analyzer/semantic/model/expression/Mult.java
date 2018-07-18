package com.nataliakt.analyzer.semantic.model.expression;

public class Mult<T1, T2> extends Expression {

    public Mult(T1 value1, T2 value2) {
        super(value1, value2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue1());
        stringBuilder.append(" * ");
        stringBuilder.append(getValue2());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
