package com.nataliakt.analyzer.semantic.model;

import java.util.ArrayList;
import java.util.List;

public class Function extends Scope {

    private final List<Variable> params;
    private Variable returnVariable;
    private boolean privated;
    private boolean main;

    public Function(String name, Scope father) {
        super(name, father);
        this.returnVariable = null;
        this.privated = true;
        this.main = false;
        this.params = new ArrayList<>();
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public void setPrivated(boolean privated) {
        this.privated = privated;
    }

    public void addParam(Variable param){
        params.add(param);
        addVariable(param);
    }

    public void setReturnVariable(Variable returnVariable) {
        this.returnVariable = returnVariable;
        this.addVariable(returnVariable);
    }

}
