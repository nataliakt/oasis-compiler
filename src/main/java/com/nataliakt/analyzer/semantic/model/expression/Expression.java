package com.nataliakt.analyzer.semantic.model.expression;

import java.util.ArrayList;
import java.util.List;

public abstract class Expression<T1, T2> {

    private T1 value1;
    private T2 value2;

    public Expression(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public List<String> getAssembly() {
        List<String> commands = new ArrayList<>();
        commands.add("mov eax, " + getValue1());
        commands.add("mov ebx, " + getValue2());
        return commands;
    }

    public T1 getValue1() {
        return value1;
    }

    public T2 getValue2() {
        return value2;
    }

}
