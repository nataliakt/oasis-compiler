package com.nataliakt.analyzer.semantic.model.expression;

import com.nataliakt.analyzer.semantic.model.Variable;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> getAssembly() {
        List<String> commands = new ArrayList<>();
        if (getValue1() instanceof Variable) {
            Variable variable = (Variable) getValue1();
            commands.addAll(variable.getExpression().getAssembly());
        } else if (getValue1() instanceof Expression) {
            Expression expression = (Expression) getValue1();
            commands.addAll(expression.getAssembly());
        } else {
            commands.add("mov eax, " + getValue1());
        }
        return commands;
    }
}
