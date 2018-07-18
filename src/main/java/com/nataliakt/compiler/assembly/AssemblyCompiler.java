package com.nataliakt.compiler.assembly;

import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.analyzer.semantic.model.Definition;
import com.nataliakt.analyzer.semantic.model.Scope;
import com.nataliakt.analyzer.semantic.model.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AssemblyCompiler {

    private SemanticAnalyzer semanticAnalyzer;

    public AssemblyCompiler(SemanticAnalyzer semanticAnalyzer) {
        this.semanticAnalyzer = semanticAnalyzer;
    }

    /**
     * Retorna as instruções em assembly
     * @param program
     * @return
     */
    public Map<String, List<String>> compile(Scope program) {
        Map<String, List<String>> vAssembly = new HashMap<>();
        program.getVariables().forEach(var -> {
            vAssembly.put(var.getName(), var.getExpression().getAssembly());
        });

        program.getChildren().forEach(child -> {
            vAssembly.putAll(compile(child));
            if (child instanceof Definition) {
                Definition definition = (Definition)child;
                vAssembly.get(definition.getVariable().getName()).addAll(definition.getExpression().getAssembly());
            }
        });

        return vAssembly;
    }

    public static String getCode(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.stream().forEach(com -> {
            stringBuilder.append(com);
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }

    public SemanticAnalyzer getSemanticAnalyzer() {
        return semanticAnalyzer;
    }
}
