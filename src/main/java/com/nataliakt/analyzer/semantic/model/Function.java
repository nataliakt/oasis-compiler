package com.nataliakt.analyzer.semantic.model;

public class Function extends Scope {

    private Variable returnVariable;
    private boolean privated;
    private boolean main;

    public Function(String name, Scope father) {
        super(name, father);
        this.returnVariable = null;
        this.privated = true;
        this.main = false;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public void setPrivated(boolean privated) {
        this.privated = privated;
    }

    public void setReturnVariable(Variable returnVariable) {
        this.returnVariable = returnVariable;
        this.addVariable(returnVariable);
    }
}
