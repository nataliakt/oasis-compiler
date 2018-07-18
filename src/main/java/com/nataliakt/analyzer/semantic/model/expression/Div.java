package com.nataliakt.analyzer.semantic.model.expression;

import com.nataliakt.analyzer.semantic.model.Variable;

import java.util.ArrayList;
import java.util.List;

public class Div<T1, T2> extends Expression {

    public Div(T1 value1, T2 value2) {
        super(value1, value2);
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

        if (getValue2() instanceof Variable) {
            commands.add("mov ecx, eax");
            Variable variable = (Variable) getValue2();
            commands.addAll(variable.getExpression().getAssembly());
            commands.add("mov ebx, eax");
            commands.add("mov eax, ecx");
        } else if (getValue2() instanceof Expression) {
            commands.add("mov ecx, eax");
            Expression expression = (Expression) getValue2();
            commands.addAll(expression.getAssembly());
            commands.add("mov ebx, eax");
            commands.add("mov eax, ecx");
        } else {
            commands.add("mov ebx, " + getValue2());
        }
        commands.add("mov edx, 0");
        commands.add("div ebx");
        return commands;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getValue1());
        stringBuilder.append(" / ");
        stringBuilder.append(getValue2());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
