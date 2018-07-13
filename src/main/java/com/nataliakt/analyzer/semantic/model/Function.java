package com.nataliakt.analyzer.semantic.model;

public class Function extends Scope {

    private Variable returnType;
    private boolean privated;
    private boolean main;

    public Function(String name, Scope father) {
        super(name, father);
        this.privated = true;
        this.main = false;
    }

    public Function(String name, Scope father, Variable returnType) {
        super(name, father);
        this.returnType = returnType;
        this.privated = true;
        this.main = false;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public void setPrivated(boolean privated) {
        this.privated = privated;
    }
}
