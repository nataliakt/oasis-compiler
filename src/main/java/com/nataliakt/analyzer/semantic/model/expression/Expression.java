package com.nataliakt.analyzer.semantic.model.expression;

public class Expression<T> {

    private T value;
    private Expression expression;

    public Expression(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value);
        return stringBuilder.toString();
    }
}
