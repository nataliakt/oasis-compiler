package com.nataliakt.analyzer.semantic.model;

import com.nataliakt.analyzer.semantic.model.expression.Expression;

import java.util.stream.Collectors;

public class Condition extends Scope {

    private Expression condition;
    private Condition fatherIf;
    private Condition conditionElse;

    /**
     * Cria o IF
     * @param father
     * @param condition
     */
    public Condition(Scope father, Expression condition) {
        super("IF", father);
        this.condition = condition;
    }

    /**
     * Cria else com condição
     * @param name
     * @param fatherIf
     * @param condition
     */
    public Condition(String name, Condition fatherIf, Expression condition) {
        super(name, fatherIf.getConditionIf().getFather());
        this.fatherIf = fatherIf;
        this.fatherIf.conditionElse = this;
        this.condition = condition;
    }

    /**
     * Cria else sem condição
     * @param name
     * @param fatherIf
     */
    public Condition(String name, Condition fatherIf) {
        super(name, fatherIf.getConditionIf().getFather());
        this.fatherIf = fatherIf;
        this.fatherIf.conditionElse = this;
    }

    public Condition getConditionIf() {
        if (fatherIf == null) {
            return this;
        }
        return fatherIf.getConditionIf();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(getName());
        stringBuilder.append(" ");
        stringBuilder.append(condition);
        stringBuilder.append(">");
        stringBuilder.append(getVariables().collect(Collectors.toList()));
        stringBuilder.append(getChildren().collect(Collectors.toList()));
        return stringBuilder.toString();
    }
}
