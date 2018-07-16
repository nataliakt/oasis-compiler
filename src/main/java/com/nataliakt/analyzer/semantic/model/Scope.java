package com.nataliakt.analyzer.semantic.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Scope {

    private final String name;
    private final Scope father;
    private final List<Scope> children;
    private final List<Variable> variables;

    public Scope(String name) {
        this.name = name;
        this.father = null;
        this.children = new ArrayList<>();
        this.variables = new ArrayList<>();
    }

    public Scope(String name, Scope father) {
        this.name = name;
        this.father = father;
        this.children = new ArrayList<>();
        this.variables = new ArrayList<>();
        this.father.valideChildren(this.name);
        this.father.children.add(this);
    }

    private boolean valideChildren(String scopeName) {
        boolean childrenExists = this.children.stream().anyMatch(child -> scopeName.equals(child.name));
        if (childrenExists) {
            throw new AlreadyExistingScopeNameException(scopeName);
        }
        return true;
    }

    private boolean validateScopeVariable(Variable variable) {
        boolean variableExists = variables.stream().anyMatch(var -> var.getName().equals(variable.getName()));
        if (variableExists) {
            throw new AlreadyExistingVariableNameException(variable.getName());
        } else if (!(this instanceof Function) && father != null) {
            return father.validateScopeVariable(variable);
        }
        return true;

    }

    public void addVariable(Variable variable) {
        if (validateScopeVariable(variable)) {
            variables.add(variable);
        }
    }

    public Scope getProgramChild() {
        if (father.father == null) {
            return this;
        }
        return father.getProgramChild();
    }

    public String getName() {
        return name;
    }

    public Scope getFather() {
        return father;
    }

    public Stream<Scope> getChildren() {
        return children.stream();
    }

    public Stream<Variable> getVariables() {
        return variables.stream();
    }

    public class AlreadyExistingScopeNameException extends RuntimeException {

        AlreadyExistingScopeNameException(String name) {
            super("O nome de escopo " + name + " já está send utilizado");
        }

    }

    public class AlreadyExistingVariableNameException extends RuntimeException {

        AlreadyExistingVariableNameException(String name) {
            super("O nome de variável " + name + " já está send utilizado");
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(variables);
        stringBuilder.append(children);
        return stringBuilder.toString();
    }
}
