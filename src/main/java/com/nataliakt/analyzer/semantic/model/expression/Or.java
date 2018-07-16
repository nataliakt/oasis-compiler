package com.nataliakt.analyzer.semantic.model.expression;

public class Or extends Expression {

    public Or(Boolean value) {
        super(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue());
        stringBuilder.append(" or ");
        stringBuilder.append(getExpression());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
